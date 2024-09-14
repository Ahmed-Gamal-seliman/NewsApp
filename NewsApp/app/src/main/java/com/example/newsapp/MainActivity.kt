package com.example.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.NewsAppTheme

class MainActivity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            NewsSourceTabs(mutableListOf(0,1,2,3,4,5,6))

        }
    }

    @Composable
    fun NewsSourceTabs(index:List<Int>) {
        var selectedTab by remember {
            mutableIntStateOf(0)
        }

        ScrollableTabRow(selectedTabIndex = selectedTab, indicator = {},
            divider = {},
            edgePadding = 0.dp,
            modifier= Modifier.padding(5.dp)
                .fillMaxWidth()) {
            index.forEachIndexed { index, i ->
                Tab(selected =  (selectedTab == index), onClick = {
                    selectedTab = index
                },
                    modifier=
                    if(selectedTab == index)  Modifier
                        .padding(8.dp)
                        .background(shape = CircleShape, color = Color.Green)
                        .border(2.dp, color = Color.White, shape = CircleShape)



                    else Modifier
                        .padding(8.dp)
                        .background(shape = CircleShape, color = Color.White)
                        .border(2.dp, color = Color.Green, shape = CircleShape)



                ) {
                    if(selectedTab == index)
                        Text(text = "Hello",color = Color.White,
                            modifier= Modifier.padding(8.dp))
                    else
                        Text(text = "Hello",color = Color.Green,
                            modifier= Modifier.padding(8.dp))
                }
            }





        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun NewsSourceTabsPreview() {
        NewsSourceTabs(mutableListOf(0,1,2,3,4,5,6))
    }

}


