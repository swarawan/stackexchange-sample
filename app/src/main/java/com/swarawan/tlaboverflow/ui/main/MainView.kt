package com.swarawan.tlaboverflow.ui.main

import com.swarawan.corelibrary.network.NetworkError
import com.swarawan.tlaboverflow.network.response.ArticleData

/**
 * Created by Rio Swarawn on 6/27/18.
 */
interface MainView {

    fun isMoreDataAvailable(): Boolean

    fun loadMoreArticles()

    fun isLoadingInProgress(): Boolean

    fun onPostFetched(data: MutableList<ArticleData>, hasMore: Boolean)

    fun setProgressLoading(visible: Boolean)

    fun onError(networkError: NetworkError)

    fun onFailed(message: String)

    fun onNetworkError()
}