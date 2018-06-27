package com.swarawan.tlaboverflow.ui.main

import com.swarawan.corelibrary.extensions.safeDispose
import com.swarawan.corelibrary.network.NetworkUtil
import com.swarawan.tlaboverflow.network.AppNetworkService
import com.swarawan.tlaboverflow.network.request.PostRequest
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Rio Swarawn on 6/27/18.
 */
class MainPresenterImpl(private val appNetworkService: AppNetworkService,
                        private val networkUtil: NetworkUtil,
                        private val view: MainView) : MainPresenter {

    private val compositeDisposable = CompositeDisposable()

    override fun getArticles(request: PostRequest) {
        when {
            networkUtil.isConnected -> {
                view.setProgressLoading(true)

                val tagged = request.tagged
                val page = request.page
                val perPage = request.perPage
                val fromDate = request.fromDate
                val toDate = request.toDate

                compositeDisposable.add(appNetworkService.getPosts(tagged, page, perPage, fromDate, toDate,
                        { response ->
                            view.setProgressLoading(false)
                            view.onPostFetched(response.items, response.hasMore)
                        },
                        { error ->
                            view.setProgressLoading(false)
                            view.onError(error)
                        }
                ))
            }
            else -> view.onNetworkError()
        }
    }

    override fun scrollAction(visibleItemCount: Int, firstVisibleItemPosition: Int, totalItemCount: Int) {
        if (view.isMoreDataAvailable()
                && !view.isLoadingInProgress()
                && (visibleItemCount + firstVisibleItemPosition >= totalItemCount)) {
            view.loadMoreArticles()
        }
    }

    override fun onDestroy() {
        compositeDisposable.safeDispose()
    }
}