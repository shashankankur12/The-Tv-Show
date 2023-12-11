package com.example.thetvshow.models

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    val results: List<TvShow>
)