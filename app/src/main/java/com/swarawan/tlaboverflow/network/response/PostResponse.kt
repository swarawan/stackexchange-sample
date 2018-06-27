package com.swarawan.tlaboverflow.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Rio Swarawn on 6/27/18.
 */
data class PostResponse(@SerializedName("items") val items: MutableList<ArticleData>,
                        @SerializedName("has_more") val hasMore: Boolean)