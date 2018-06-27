package com.swarawan.tlaboverflow.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Rio Swarawn on 6/27/18.
 */
data class ArticleData(@SerializedName("tags") val tags: MutableList<String>,
                       @SerializedName("owner") val owner: OwnerData,
                       @SerializedName("is_answered") val isAnswered: Boolean,
                       @SerializedName("view_count") val viewCount: Int,
                       @SerializedName("answer_count") val answerCount: Int,
                       @SerializedName("last_activity_date") val lastActivityDate: Long,
                       @SerializedName("creation_date") val creationDate: Long,
                       @SerializedName("last_edit_date") val lastEditDate: Long,
                       @SerializedName("question_id") val questionId: Long,
                       @SerializedName("link") val link: String,
                       @SerializedName("title") val title: String)