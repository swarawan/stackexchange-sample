package com.swarawan.tlaboverflow.network

import com.swarawan.tlaboverflow.network.response.PostResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by rioswarawan on 5/1/18.
 */
interface NetworkService {

    @GET("search")
    fun getPosts(
            @Query("tagged") tag: String,
            @Query("page") page: String,
            @Query("pagesize") pageSize: String,
            @Query("fromdate") fromDate: String,
            @Query("todate") toDate: String,
            @Query("order") order: String = "desc",
            @Query("sort") sort: String = "activity",
            @Query("site") site: String = "stackoverflow"): Flowable<PostResponse>
}