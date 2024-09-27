package com.example.newsapp

import CallNewsSourcesTabsApi
import NewsSourceTabs
import android.os.Build
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
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.newsapp.API.ApiManager
import com.example.newsapp.API.Model.ArticlesItem
import com.example.newsapp.API.Model.NewsResponse
import com.example.newsapp.API.Model.SourceResponse
import com.example.newsapp.API.Model.SourcesItem
import com.example.newsapp.CustomNavType.articleNavType
import com.example.newsapp.ui.theme.NewsAppTheme
import com.example.newsapp.ui.theme.dateColor
import com.example.newsapp.ui.theme.titleColor
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import retrofit2.Call
import retrofit2.Response
import java.util.Locale
import javax.security.auth.callback.Callback
import kotlin.reflect.KProperty
import kotlin.reflect.typeOf

class MainActivity : ComponentActivity() {
    private var list: List<SourcesItem?>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NavigateToNewsScreen()

//            CircularProgressBar(true)
//            DropDownMenu()
//            SearchAppBar()

//            HomeScreen()
//            {
//
//                categoryClickedName= it
//
//            }


//            if(sourcesList.isNotEmpty())
//            {
//                /* Navigate to News Screen */
//
//            }


        }

    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Composable
    fun NavigateToNewsScreen() {
        var categoryClickedName by remember {
            mutableStateOf("")
        }
        val sourcesList = remember {
            mutableStateListOf<SourcesItem?>()
        }

        var sourceId by remember {
            mutableStateOf("")
        }

        val newsList = remember {
            mutableStateListOf<ArticlesItem?>()
        }


        if (categoryClickedName.isNotEmpty()) {

            CallNewsSourcesTabsApi(categoryClickedName)
            {
                newsList.clear()
                sourcesList.clear()
                sourcesList.addAll(it.toList())

            }
        }

        if (sourceId.isNotEmpty()) {
            newsList.clear()
            CallNewsSourcesApi(sourceId)
            {

                it?.let {
                    newsList.clear()
                    newsList.addAll(it)
                }

            }
        }


        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "Main Categories") {
            composable(route = "Main Categories") {

                NavigationDrawer(navigateToDrawerScreen = { categoryName ->
                    if (categoryName == "Categories") {
                        if (navController.currentDestination?.route != "Main Categories")
                            navController.navigate("Main Categories")
//                        navController.popBackStack()
                    } else if (categoryName == "Settings") {
                        Log.e("settings", "yes")
                        if (navController.currentDestination?.route != "settings Screen")
                            navController.navigate("settings Screen")
//                        navController.popBackStack()
                    }
                }) {

                    HomeScreen(onMenuItemClicked = { isDrawerOpened ->
                        it(isDrawerOpened)
                    }) {
                        Log.e("category", "yes")
                        categoryClickedName = it
                        navController.navigate("Category Screen")
                    }
                }

            }
            composable(route = "Category Screen")
            {
                NavigationDrawer(navigateToDrawerScreen = { categoryName ->
                    if (categoryName == getString(R.string.categories)) {
                        Log.e("categories", "yes")
                        if (navController.currentDestination?.route != "Main Categories")
                            navController.navigate("Main Categories")

                    } else if (categoryName == getString(R.string.settings)) {
                        if (navController.currentDestination?.route != "settings Screen")
                            navController.navigate("settings Screen")
                    }
                }) {
                    NewsScreen(
                        categoryName = categoryClickedName,
                        sourcesList = sourcesList.toPersistentList(),
                        sourceArticlesList = newsList.toPersistentList(),
                        onSearchIconClicked = {
                            if (it) {
                                Log.e("now", "one")
                                if (newsList.size != 0) {

                                    navController.navigate("SearchRoute")


                                }
                                Log.e("now", "two")

                            }
                        },
                        onMenuItemClicked = { isDrawerOpened ->
                            it(isDrawerOpened)
                        },
                        onItemClicked={
                            if(it != null)
                                navController.navigate(Details(it))
                        }
                    )
                    { id ->
                        sourceId = id

                    }
                }


            }

            composable(route = "settings Screen")
            {
                NavigationDrawer(navigateToDrawerScreen = { categoryName ->
                    if (categoryName == "Categories") {
                        Log.e("categories", "yes")
                        if (navController.currentDestination?.route != "Main Categories")
                            navController.navigate("Main Categories")

                    } else if (categoryName == "Settings") {
                        if (navController.currentDestination?.route != "settings Screen")
                            navController.navigate("settings Screen")
                    }
                }) {
                    SettingsScreen(languageChange = {
                        language->
                        if(language == Data.Arabic)
                        {
                            convertAppToArabic()
                        }
                        else if(language == Data.English)
                        {
                            convertAppToEnglish()
                        }

                    }) { isDrawerOpened ->
                        it(isDrawerOpened)
                    }
                }
            }


            composable<Details>(typeMap = mapOf(typeOf<ArticlesItem>() to articleNavType))
             {
                                 val article = it.toRoute<Details>()
                DetailsScreen(categoryName = categoryClickedName, article = article.article)
            }




            composable("SearchRoute") {

                SearchScreen(sourceId)
                {
                    if(it != null)
                        navController.navigate(Details(it))
                }

            }
//

        }


    }

    @Composable
    fun NewsScreen(
        categoryName: String,
        sourcesList: PersistentList<SourcesItem?>,
        sourceArticlesList: PersistentList<ArticlesItem?>,

        onMenuItemClicked: (Boolean) -> Unit = {},
        onSearchIconClicked: (searchClicked: Boolean) -> Unit = {},
        onItemClicked:(article:ArticlesItem?) ->Unit,
        onTabClicked: (sourceId: String) -> Unit

    ) {


        Column()
        {
            ToolbarHeader(title = categoryName, hasMenuItem = true,
                hasSearchItem = true,
                onSearchIconClicked = {
                    onSearchIconClicked(it)
                }
            )
            {
                onMenuItemClicked(it)
            }

            NewsSourceTabs(sourceItems = sourcesList.toPersistentList())
            { sourceId ->
                onTabClicked(sourceId)
            }
            NewsList(sourceArticlesList)
            {
               articleItem->
                onItemClicked(articleItem)
            }

//            NewsList(articlesList = newArticleList)


        }
    }

    private fun convertAppToArabic() {
        if (resources.configuration.locale.language != Locale.forLanguageTag("ar").toString()) {
            val config = this.resources?.configuration
            config?.setLocale(Locale.forLanguageTag("ar"))
            this.resources?.updateConfiguration(
                config,
                this.resources.displayMetrics
            )
            recreate()
        }
    }

    private fun convertAppToEnglish() {

        if (resources.configuration.locale.language != Locale.ENGLISH.toString()) {
            val config = this.resources?.configuration
            config?.setLocale(Locale.ENGLISH)
            this.resources?.updateConfiguration(config, this.resources.displayMetrics)
            recreate()
        }
    }
}

















