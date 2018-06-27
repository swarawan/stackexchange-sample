package com.swarawan.tlaboverflow.network.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Rio Swarawn on 6/27/18.
 */
data class OwnerData(@SerializedName("reputation") val reputation: Int,
                     @SerializedName("user_id") val userId: Int,
                     @SerializedName("user_type") val userType: String,
                     @SerializedName("profile_image") val profileImage: String,
                     @SerializedName("display_name") val displayName: String,
                     @SerializedName("link") val link: String)