package com.example.thetvshow.api

import com.example.thetvshow.models.ApiResponse
import com.example.thetvshow.models.TvShow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvApi {
    @GET("trending/tv/day")
    suspend fun getTrendingTvShows(): Response<ApiResponse>

    @GET("search/tv")
    suspend fun searchTvShow(@Query("query") query: String): Response<ApiResponse>

    @GET("tv/{series_id}")
    suspend fun getTvShow(@Path("series_id") seriesId: String): Response<TvShow>
}