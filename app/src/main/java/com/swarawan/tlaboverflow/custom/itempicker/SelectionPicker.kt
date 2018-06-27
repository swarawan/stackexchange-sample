package com.swarawan.tlaboverflow.custom.itempicker

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swarawan.tlaboverflow.R
import io.reactivex.Observable
import kotlinx.android.synthetic.main.layout_bottomsheet.*

/**
 * Created by rioswarawan on 4/13/18.
 */
class SelectionPicker : BottomSheetDialogFragment(), SelectionView {

    companion object {
        fun newInstance() = SelectionPicker()
    }

    private val presenter: SelectionPresenter by lazy { SelectionPresenter(this@SelectionPicker) }
    private lateinit var selected: Selection
    lateinit var listener: SelectCategoryListener
    var requestType: Int = 0
    var selections = mutableListOf<Selection>()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.layout_bottomsheet, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        with(recyclerViewSelection) {
            layoutManager = LinearLayoutManager(context)
            adapter = SelectionAdapter(selections)
        }
        buttonSelect.isEnabled = false

        presenter.observeSelection {
            buttonSelect.isEnabled = it.isSelected
            selected = it
        }

        buttonSelect.setOnClickListener {
            listener.onSelectedCategory(selected, requestType)
            dismiss()
        }
    }

    interface SelectCategoryListener {
        fun onSelectedCategory(selected: Selection, requestType: Int)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDestroy()
    }

    override fun selectionObservable(): Observable<Selection> =
            (recyclerViewSelection.adapter as SelectionAdapter).publisher
                    .flatMapIterable { it }
                    .map { it }
}