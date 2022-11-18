package com.chalchap7.challengechapter7.model

import com.google.gson.annotations.SerializedName

data class ResponMovieItem(
    @SerializedName("page")
    val page: Int,
    @SerializedName("backdrop_path")
    val backdrop_path: String?,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)