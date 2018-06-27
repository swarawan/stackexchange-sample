package com.swarawan.tlaboverflow.custom.itempicker

import io.reactivex.Observable

/**
 * Created by rioswarawan on 4/13/18.
 */
interface SelectionView {

    fun selectionObservable(): Observable<Selection>

}