package com.example.thetvshow.models

import com.google.gson.annotations.SerializedName

data class TvShow(
    val id: Int,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("original_name")
    val originalName: String,
    val name: String,
    val overview: String
)