package com.example.newsapp

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.API.ApiManager
import com.example.newsapp.API.Model.ArticlesItem
import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourceResponse
import com.example.newsapp.ui.theme.closeIconColor
import com.example.newsapp.ui.theme.searchIconColor
import com.example.newsapp.ui.theme.toolBarColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.serialization.Serializable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

@Serializable
data class SearchRoute(val sourceId:String)
//val articlesList: PersistentList<ArticlesItem?>
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    onTextChanged: (String) -> Unit,
    onDeleteText: (Boolean) -> Unit,
) {

    var searchWord by remember {
        mutableStateOf("")
    }
    OutlinedTextField(

        value = searchWord,
        onValueChange = {
            searchWord = it
            onTextChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 24.dp, bottomStart = 24.dp))
            .background(color = toolBarColor)
            .padding(15.dp),

        shape = RoundedCornerShape(25.dp),

        leadingIcon = {
            IconButton(onClick = {
                searchWord=""
                onDeleteText(true)
            }) {

                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Close icon",
                    tint = closeIconColor
                )
            }
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search icon",
                tint = searchIconColor
            )

        },
        placeholder = {
            Text(text = "Search Article")
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = Color.Transparent,
            containerColor = Color.White
        )

    )
}

fun searchOnNews(
    sourceArticlesList: List<ArticlesItem?>,
    searchQuery: String,
): List<ArticlesItem?> {
    val newList = sourceArticlesList.filter {
        it?.author?.toLowerCase(Locale.ROOT)?.contains(searchQuery.toLowerCase(Locale.ROOT)) ?: false
    }
newList.forEach {
    Log.e("title",it?.author ?: "")
}
    return newList
}

@Composable
fun SearchScreen(sourceId:String,
                 onItemClicked:(article:ArticlesItem?)->Unit) {
//articlesList: PersistentList<ArticlesItem?>
var searchWord by remember {
    mutableStateOf("")
}
val newsList= remember {
    mutableStateListOf<ArticlesItem?>()
}
val searchedList= remember {
    mutableStateListOf<ArticlesItem?>()
}
    if(newsList.toList().isNotEmpty())
    {
        Log.e("list","list is not empty")
    }
    else
        Log.e("list","list is empty")
    LaunchedEffect(key1 = Unit) {
        ApiManager.getNewsSources().getNewsBySource(sourceId = sourceId).enqueue(object:Callback<NewsResponse>{
            override fun onResponse(p0: Call<NewsResponse>, response: Response<NewsResponse>) {

                val list = response.body()?.articles ?: listOf(ArticlesItem())
                newsList.addAll(list)
                searchedList.addAll(list)

            }

            override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {

            }
        })

    }
    Column()
    {
        SearchAppBar(onTextChanged = {
            searchWord=it
            searchedList.clear()
            searchedList.addAll(searchOnNews(newsList.toList(),searchWord))
        })
        {
            if(it)
            {
                searchedList.clear()
                searchedList.addAll(newsList.toList())
            }

        }
        NewsList(articlesList = searchedList.toList())
        {
            onItemClicked(it)
        }

    }



}

@Preview
@Composable
private fun SearchAppBarPreview() {
    SearchAppBar(onTextChanged = {})
    {

    }
}