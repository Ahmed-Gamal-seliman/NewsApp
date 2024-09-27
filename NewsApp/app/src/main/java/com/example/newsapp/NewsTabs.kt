import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.newsapp.API.ApiManager
import com.example.newsapp.API.Model.SourceResponse
import com.example.newsapp.API.Model.SourcesItem
import com.example.newsapp.CircularProgressBar
import com.example.newsapp.ui.theme.toolBarColor
import kotlinx.collections.immutable.PersistentList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun NewsSourceTabs(
    sourceItems: PersistentList<SourcesItem?>,
    onTabClicked: (String) -> Unit
) {

    var selectedTab by remember {
        mutableIntStateOf(0)
    }

    /* To select the first tab by default and get news */
    if( (sourceItems.isNotEmpty()) && (selectedTab ==0))
    {
        onTabClicked(sourceItems[0]?.id ?: "")
    }


Log.e("selectedtab",selectedTab.toString())
    ScrollableTabRow(
        selectedTabIndex = selectedTab, indicator = {},
        divider = {},
        edgePadding = 0.dp,
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ) {
        sourceItems.forEachIndexed { index, item ->
            Tab(
                selected =  (selectedTab == index),
                onClick = {
                    selectedTab = index
                    onTabClicked(sourceItems[index]?.id ?: "")

                },
                modifier =
                if (selectedTab == index) Modifier
                    .padding(8.dp)
                    .background(shape = CircleShape, color = toolBarColor)
                    .border(2.dp, color = Color.White, shape = CircleShape)
                else Modifier
                    .padding(8.dp)
                    .background(shape = CircleShape, color = Color.White)
                    .border(2.dp, color = toolBarColor, shape = CircleShape)


            ) {
                if (selectedTab == index)
                    Text(
                        text = "${item?.name}", color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                else
                    Text(
                        text = "${item?.name}", color = toolBarColor,
                        modifier = Modifier.padding(8.dp)
                    )
            }
        }


    }

}


@Composable
fun CallNewsSourcesTabsApi(categoryName:String,getList: (List<SourcesItem?>) -> Unit) {

    var isAppearProgressBar by remember {
        mutableStateOf(false)
    }
    if(isAppearProgressBar)
    {
        CircularProgressBar(isAppearProgressBar)
    }
    LaunchedEffect(key1 = categoryName) {
        isAppearProgressBar=true
        ApiManager.getNewsSources().getSources(category = categoryName).enqueue(
            object : Callback<SourceResponse> {
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {

                    if (response.isSuccessful) {
                        isAppearProgressBar=false
                        response.body()?.sources?.let {

                            getList(it)
                        }
                    }


                }

                override fun onFailure(p0: Call<SourceResponse>, p1: Throwable) {

                }
            }
        )

    }

}

