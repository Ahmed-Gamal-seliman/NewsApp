package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.newsapp.API.ApiManager
import com.example.newsapp.API.Model.SourceResponse
import com.example.newsapp.API.Model.SourcesItem
import com.example.newsapp.ui.theme.NewsAppTheme
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CallNewsSourcesTabsApi()


            }
        }
    }


@Composable
fun CallNewsSourcesTabsApi() {


    LaunchedEffect(key1 = true) {

        ApiManager.getNewsSources().getSources().enqueue(
            object : retrofit2.Callback<SourceResponse> {
                override fun onResponse(call: Call<SourceResponse>, response: Response<SourceResponse>) {



                }

                override fun onFailure(p0: Call<SourceResponse>, p1: Throwable) {

                }
            }
        )
    }

}
