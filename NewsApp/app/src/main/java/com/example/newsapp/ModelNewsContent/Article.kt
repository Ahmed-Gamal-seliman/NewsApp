package com.example.newsapp.ModelNewsContent

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


//@Parcelize
data class Article(

    @field:SerializedName("publishedAt")
	val publishedAt: String? = null,

    @field:SerializedName("author")
	val author: String? = null,

    @field:SerializedName("urlToImage")
	val urlToImage: String? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("source")
	val source:   /* @RawValue */Source? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("url")
	val url: String? = null,

    @field:SerializedName("content")
	val content: String? = null
)//:Parcelable