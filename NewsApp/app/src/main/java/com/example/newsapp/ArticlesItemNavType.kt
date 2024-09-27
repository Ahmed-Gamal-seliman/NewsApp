package com.example.newsapp

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.example.newsapp.API.Model.ArticlesItem
import com.google.gson.Gson
import kotlinx.collections.immutable.PersistentList
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
object CustomNavType{
    val articleNavType = object: NavType<ArticlesItem>(isNullableAllowed = false)
    {
        override fun get(bundle: Bundle, key: String): ArticlesItem?{
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun parseValue(value: String): ArticlesItem {
            return Json.decodeFromString<ArticlesItem>(Uri.decode(value))
        }


        override fun serializeAsValue(value: ArticlesItem): String {
            return Uri.encode(Json.encodeToString(value))
        }
        override fun put(bundle: Bundle, key: String, value: ArticlesItem) {
            bundle.putString(key,Json.encodeToString(value))
        }
    }


}