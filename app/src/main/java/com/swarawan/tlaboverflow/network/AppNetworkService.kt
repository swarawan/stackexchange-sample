package com.swarawan.tlaboverflow.network

import com.swarawan.corelibrary.extensions.uiSubscribe
import com.swarawan.corelibrary.network.NetworkError
import com.swarawan.corelibrary.utils.TextUtils
import com.swarawan.tlaboverflow.network.response.PostResponse
import io.reactivex.disposables.Disposable

/**
 * Created by rioswarawan on 5/2/18.
 */
class AppNetworkService(val networkService: NetworkService) {

    inline fun getPosts(tagged: String, page: String, pageSize: String, fromDate: String, toDate: String,
                        crossinline onSuccess: (PostResponse) -> Unit,
                        crossinline onError: (NetworkError) -> Unit): Disposable {
        return networkService.getPosts(tagged, page, pageSize, fromDate, toDate)
                .uiSubscribe(
                        { onSuccess(it) },
                        { onError(NetworkError(it)) })
    }
}