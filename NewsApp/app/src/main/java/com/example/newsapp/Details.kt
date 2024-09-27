package com.example.newsapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.API.Model.ArticlesItem
import com.example.newsapp.R
import com.example.newsapp.ToolbarHeader
import kotlinx.serialization.Serializable


@Serializable
data class Details(val article:ArticlesItem)
@Composable
fun DetailsScreen(categoryName:String,article:ArticlesItem) {
    Column() {
        /* Toolbar */
        ToolbarHeader(title=categoryName)

        /* Spacer */
        Spacer(modifier = Modifier.height(50.dp))
    Column(modifier= Modifier.padding(15.dp)) {
        NewsItem(article = article)
        {

        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = article.description ?: "")
    }

    }

}

@Preview(showBackground = true)
@Composable
private fun DetailsScreenPreview() {
//    DetailsScreen()
}