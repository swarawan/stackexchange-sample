package com.swarawan.tlaboverflow.network.request

import com.google.gson.annotations.SerializedName

/**
 * Created by Rio Swarawn on 6/27/18.
 */
data class PostRequest(@SerializedName("tagged") val tagged: String,
                       @SerializedName("tagged") val page: String,
                       @SerializedName("perPage") val perPage: String,
                       @SerializedName("fromDate") val fromDate: String,
                       @SerializedName("toDate") val toDate: String)