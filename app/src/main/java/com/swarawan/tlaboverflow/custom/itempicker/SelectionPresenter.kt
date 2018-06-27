package com.swarawan.tlaboverflow.custom.itempicker

import com.swarawan.corelibrary.extensions.safeDispose
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by rioswarawan on 4/13/18.
 */
class SelectionPresenter(val view: SelectionView) {

    var compositeDisposable = CompositeDisposable()

    inline fun observeSelection(crossinline func: (item: Selection) -> Unit) {
        compositeDisposable.add(view.selectionObservable().subscribe { func(it) })
    }

    fun onDestroy() {
        compositeDisposable.safeDispose()
    }

}