package com.example.newsapp

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.API.ApiManager
import com.example.newsapp.API.Model.ArticlesItem
import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourceResponse
import com.example.newsapp.API.Model.SourcesItem
import com.example.newsapp.ui.theme.dateColor
import com.example.newsapp.ui.theme.titleColor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsList(
    articlesList: List<ArticlesItem?>,
    onItemClicked: (article: ArticlesItem?) -> Unit,
) {

                LazyColumn(modifier = Modifier.padding(15.dp)) {

                    items(articlesList.size) { index ->

                        /* News Item */
                        NewsItem(articlesList[index]) { articleItem ->
                            onItemClicked(articleItem)
                        }

                    }
                }





}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NewsItem(
    article: ArticlesItem?,
    onItemClicked: (article: ArticlesItem?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Card(elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
            modifier = Modifier
                .padding(vertical = 5.dp)
                .clickable {
                    onItemClicked(article)
                }) {

            /* Image */
            GlideImage(
                model = article?.urlToImage,
                contentDescription = "article image",
                loading = placeholder(R.drawable.ic_launcher_foreground),
                modifier = Modifier.fillMaxWidth(),
                alignment = Alignment.Center
            )

            /* Title */
            Text(
                text = article?.author ?: "", color = titleColor,
                fontSize = 15.sp
            )
            /* Description */
            Text(text = article?.description ?: "", color = Color.Black)

            /* time*/
            Text(
                text = article?.publishedAt ?: "", color = dateColor,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}


@Composable
fun CallNewsSourcesApi(
    sourceId: String,
    onResponseReturned: (List<ArticlesItem?>?) -> Unit,
) {
    var isAppearProgressBar by remember {
        mutableStateOf(false)
    }
    if (isAppearProgressBar) {
        CircularProgressBar(isAppear = isAppearProgressBar)
    }
    LaunchedEffect(key1 = sourceId) {
        /* Appear Progress bar for sources */
        isAppearProgressBar = true

        ApiManager.getNewsSources().getNewsBySource(sourceId = sourceId).enqueue(
            object : retrofit2.Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>,
                ) {
                    onResponseReturned(response.body()?.articles)
                    /* Appear Progress bar for sources */
                    isAppearProgressBar = false

                }

                override fun onFailure(p0: Call<NewsResponse>, p1: Throwable) {

                }
            }

        )
    }
}