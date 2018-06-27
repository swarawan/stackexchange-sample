package com.swarawan.tlaboverflow.ui.main

import com.swarawan.tlaboverflow.network.request.PostRequest

/**
 * Created by Rio Swarawn on 6/27/18.
 */
interface MainPresenter {

    fun getArticles(request: PostRequest)

    fun scrollAction(visibleItemCount: Int, firstVisibleItemPosition: Int, totalItemCount: Int)

    fun onDestroy()
}