package com.swarawan.tlaboverflow.ui.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import com.jakewharton.rxbinding2.widget.RxTextView
import com.swarawan.corelibrary.base.CoreActivity
import com.swarawan.corelibrary.extensions.blockTouchScreen
import com.swarawan.corelibrary.extensions.goneIf
import com.swarawan.corelibrary.extensions.showDialog
import com.swarawan.corelibrary.extensions.unblockTouchScreen
import com.swarawan.corelibrary.network.NetworkError
import com.swarawan.corelibrary.network.NetworkUtil
import com.swarawan.corelibrary.utils.TextUtils
import com.swarawan.tlaboverflow.R
import com.swarawan.tlaboverflow.TlabApp
import com.swarawan.tlaboverflow.common.convertDate
import com.swarawan.tlaboverflow.custom.datepicker.DatePicker
import com.swarawan.tlaboverflow.custom.itempicker.Selection
import com.swarawan.tlaboverflow.custom.itempicker.SelectionPicker
import com.swarawan.tlaboverflow.database.AppPreferences
import com.swarawan.tlaboverflow.network.AppNetworkService
import com.swarawan.tlaboverflow.network.request.PostRequest
import com.swarawan.tlaboverflow.network.response.ArticleData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_toolbar_default.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : CoreActivity(), MainView {

    @Inject
    lateinit var appNetworkService: AppNetworkService

    @Inject
    lateinit var networkUtil: NetworkUtil

    @Inject
    lateinit var appPreference: AppPreferences

    private var currentPage: Int = 1
    private var mainAdapter: MainAdapter? = null
    private val linearLayoutManager = LinearLayoutManager(this@MainActivity)
    private val subjectSearch: Subject<String> = PublishSubject.create()
    private var hasMore = true
    private var isLoadingProgress: Boolean = false

    private val presenter: MainPresenter by lazy {
        MainPresenterImpl(appNetworkService, networkUtil, this@MainActivity)
    }

    override fun setLayout() {
        setContentView(R.layout.activity_main)

        setSupportActionBar((layoutToolbarDefault as Toolbar))
        toolbarTitle.text = resources.getString(R.string.app_name)
    }

    override fun initInjection() {
        (application as TlabApp).defaultComponent.inject(this@MainActivity)
    }

    override fun onViewReady(savedInstanceState: Bundle?) {
        mainAdapter = MainAdapter(mutableListOf())
        recyclerViewArticle.apply {
            adapter = mainAdapter
            layoutManager = linearLayoutManager
        }

        setupScroll()
        setupArticles()
        observeInputSearch()

        textFromResult.setOnClickListener { showFromDateDialog() }
        textToResult.setOnClickListener { showToDateDialog() }
        textPerPageResult.setOnClickListener { showPerPageDialog() }
    }

    private fun setupArticles() {
        val tagged = appPreference.tag
        val perPage = appPreference.perPage
        val fromDate = appPreference.fromDate
        val toDate = appPreference.toDate

        textPerPageResult.text = perPage
        if (tagged.isNotEmpty()) inputSearchTag.hint = "Last search: $tagged"
        if (fromDate.isNotEmpty()) textFromResult.text = (fromDate.toLong() * 1000).convertDate()
        if (toDate.isNotEmpty()) textToResult.text = (toDate.toLong() * 1000).convertDate()
    }

    private fun setupScroll() {
        recyclerViewArticle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = linearLayoutManager.childCount
                val totalItemCount = linearLayoutManager.itemCount
                val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
                presenter.scrollAction(visibleItemCount, firstVisibleItemPosition, totalItemCount)
            }
        })
    }

    private fun observeInputSearch() {
        RxTextView.textChanges(inputSearchTag)
                .observeOn(AndroidSchedulers.mainThread())
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter { it.toString().isNotEmpty() || it.toString().length > 2 }
                .map { it.toString() }
                .subscribe { subjectSearch.onNext(it) }

        subjectSearch.observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("popow", it)
                    if (it.isNotEmpty()) appPreference.tag = it
                    refreshArticles()
                }
    }

    private fun showFromDateDialog() {
        val dialog = DatePicker.newInstance().apply {
            listener = object : DatePicker.OnDatePickListener {
                override fun onDatePicked(dateString: String, timeInMilis: String) {
                    this@MainActivity.textFromResult.text = dateString
                    appPreference.fromDate = timeInMilis
                    refreshArticles()
                }
            }
        }
        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun showToDateDialog() {
        val dialog = DatePicker.newInstance().apply {
            listener = object : DatePicker.OnDatePickListener {
                override fun onDatePicked(dateString: String, timeInMilis: String) {
                    this@MainActivity.textToResult.text = dateString
                    appPreference.toDate = timeInMilis
                    refreshArticles()
                }
            }
        }
        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun showPerPageDialog() {
        val selections = mutableListOf<Selection>().apply {
            add(Selection("10", false))
            add(Selection("20", false))
            add(Selection("30", false))
            add(Selection("50", false))
        }

        val dialog = SelectionPicker.newInstance().apply {
            selections.addAll(selections)
            listener = object : SelectionPicker.SelectCategoryListener {
                override fun onSelectedCategory(selected: Selection, requestType: Int) {
                    this@MainActivity.textPerPageResult.text = selected.name
                    appPreference.perPage = selected.name
                    refreshArticles()
                }
            }
        }
        dialog.show(supportFragmentManager, dialog.tag)
    }

    private fun refreshArticles() {
        mainAdapter?.let {
            it.newList(mutableListOf())
            loadMoreArticles()
        }
    }

    override fun isMoreDataAvailable(): Boolean = hasMore

    override fun loadMoreArticles() {
        if (appPreference.tag.isEmpty()) return
        if (hasMore) {
            val request = PostRequest(
                    appPreference.tag,
                    currentPage.toString(),
                    appPreference.perPage,
                    appPreference.fromDate,
                    appPreference.toDate)

            presenter.getArticles(request)
        }
    }

    override fun isLoadingInProgress(): Boolean = isLoadingProgress

    override fun onPostFetched(data: MutableList<ArticleData>, hasMore: Boolean) {
        mainAdapter?.let {
            it.updateList(data)

            this.currentPage += 1
            this.hasMore = hasMore
        }
    }

    override fun setProgressLoading(visible: Boolean) {
        isLoadingProgress = visible
        progressBar.goneIf(!visible)
        window?.let {
            when {
                visible -> it.blockTouchScreen()
                else -> it.unblockTouchScreen()
            }
        }
    }

    override fun onError(networkError: NetworkError) {
        showDialog(resources.getString(R.string.text_message_dialog_failed_data), false,
                resources.getString(R.string.text_yes))
    }

    override fun onFailed(message: String) {
        showDialog(message, false, resources.getString(R.string.text_yes))
    }

    override fun onNetworkError() {
        showDialog(resources.getString(R.string.text_message_dialog_no_connection), false,
                resources.getString(R.string.text_yes))
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
