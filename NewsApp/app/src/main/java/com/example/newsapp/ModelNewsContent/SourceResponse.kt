package com.example.newsapp.ModelNewsContent

import android.os.Parcelable
import com.google.gson.annotations.SerializedName



data class SourceResponse(

    @field:SerializedName("totalResults")
	val totalResults: Int? = null,

    @field:SerializedName("articles")
	val articles: ArrayList<Article?>? = null,

    @field:SerializedName("status")
	val status: String? = null
)