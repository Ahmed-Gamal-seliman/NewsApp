package com.example.newsapp

import CallNewsSourcesTabsApi
import android.annotation.SuppressLint
import android.graphics.fonts.FontStyle
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column


import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight


import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.API.Model.Category
import com.example.newsapp.API.Model.NavigationDrawerItem
import com.example.newsapp.ui.theme.businessBgColor
import com.example.newsapp.ui.theme.drawerHeaderColor
import com.example.newsapp.ui.theme.enviromentBgColor
import com.example.newsapp.ui.theme.healthBgColor
import com.example.newsapp.ui.theme.politicsBgColor
import com.example.newsapp.ui.theme.scienceBgColor
import com.example.newsapp.ui.theme.sportsBgColor
import com.example.newsapp.ui.theme.toolBarColor
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    onMenuItemClicked: (Boolean) -> Unit = {},
    onCategoryItemClicked: (categoryName: String) -> Unit
) {

    val categoriesList=  listOf(
        Category(
            categoryBackground = sportsBgColor,
            categoryName = stringResource(id = R.string.sports)
            ,
            categoryImage = R.drawable.sports
        ),
        Category(
            categoryBackground = politicsBgColor,
            categoryName = stringResource(R.string.politics),
            categoryImage = R.drawable.politics
        ),
        Category(
            categoryBackground = healthBgColor,
            categoryName = stringResource(R.string.health),
            categoryImage = R.drawable.health
        ),
        Category(
            categoryBackground = businessBgColor,
            categoryName = stringResource(R.string.business),
            categoryImage = R.drawable.bussines
        ),
        Category(
            categoryBackground = enviromentBgColor,
            categoryName = stringResource(R.string.enviroment),
            categoryImage = R.drawable.environment
        ),
        Category(
            categoryBackground = scienceBgColor,
            categoryName = stringResource(R.string.science),
            categoryImage = R.drawable.science
        )
    )
//    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
//    var isDrawerOpened by remember {
//        mutableStateOf(false)
//    }
//    var secondDrawerState by remember {
//        mutableStateOf(false)
//    }
//
//    var selectedItemIndex by remember {
//        mutableStateOf(0)
//    }
//
//    val scope = rememberCoroutineScope()


//    LaunchedEffect(key1 = secondDrawerState) {
//        if (isDrawerOpened)
//            drawerState.open()
//        else
//            drawerState.close()
//    }

    Column(
        verticalArrangement = Arrangement.Center

    ) {


        /* Header Toolbar */
        ToolbarHeader(stringResource(R.string.news_app_toolBar), hasMenuItem = true) { drawerOpened ->
            onMenuItemClicked(drawerOpened)

        }


        /* Text Header */
        Text(
            text = stringResource(R.string.pick_your_category_of_interest),
            modifier = Modifier.padding(24.dp),
            fontSize = 20.sp
        )/* List of main Items */
        MainListItems(categoriesList) { name ->
            onCategoryItemClicked(name)
        }


        /* Call Api */

        /* Navigate to sports screen */

    }
}


@Preview(showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    NavigationDrawer({

    })
    {

        HomeScreen(onMenuItemClicked = { isDrawerOpened ->
            it(isDrawerOpened)
        }) {

        }

    }
}

@Composable
fun NavigationDrawer(
    navigateToDrawerScreen: (String)->Unit,
    screen: @Composable (isDrawerOpenedFun: (Boolean) -> Unit) -> Unit
) {
    var selectedItemIndex by remember {
        mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()

    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var isDrawerOpened by remember {
        mutableStateOf(false)
    }
    var isSecondDrawerOpened by remember {
        mutableStateOf(false)
    }

    val navigationList = listOf<NavigationDrawerItem>(
        NavigationDrawerItem(stringResource(R.string.categories),
            iconImage = R.drawable.ic_categories),

        NavigationDrawerItem(stringResource(R.string.settings),
            iconImage = R.drawable.ic_settings)

    )

    LaunchedEffect(key1 = isSecondDrawerOpened) {
        if (isDrawerOpened)
            drawerState.open()
        else
            drawerState.close()
    }


    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                navigationList.forEachIndexed()
                { index, navigationItem ->
                    NavigationDrawerItem(
                        label = {
                        Text(
                            text = navigationItem.iconName,
                            fontSize = 15.sp
                        )
                     }, selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                navigateToDrawerScreen(navigationItem.iconName)
                                drawerState.close()
                            }


                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navigationItem.iconImage),
                                contentDescription = "icon Image",
                                modifier = Modifier.size(12.dp)
                            )
                        },
                    )
                }


            }

        },
        drawerState = drawerState
    )
    {
        screen { isDrawerOpenedParam ->
            isDrawerOpened = isDrawerOpenedParam
            isSecondDrawerOpened = !isSecondDrawerOpened
        }
    }
}

@Composable
fun DrawerHeader() {
    Box(
        modifier = Modifier
            .background(color = drawerHeaderColor)
            .fillMaxWidth()
            .fillMaxHeight(0.1F)
            .padding(5.dp)
    ) {
        Text(
            text = stringResource(R.string.news_app),
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            fontSize = 25.sp
        )
    }

}

@Preview(showBackground = true)
@Composable
private fun DrawerHeaderPreview() {
    DrawerHeader()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolbarHeader(
    title: String,
    hasSearchItem: Boolean = false,
    hasMenuItem: Boolean = false,
    onSearchIconClicked:(Boolean)->Unit={},
    currentDrawerState: (isDrawerOpened: Boolean) -> Unit = {}
) {


    val scopeCoroutine = rememberCoroutineScope()


            CenterAlignedTopAppBar(title = {
                Text(
                    text = title, color = Color.White
                )
            },
                modifier = Modifier.clip(
                    RoundedCornerShape(
                        bottomEnd = 24.dp,
                        bottomStart = 24.dp
                    )
                ),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = toolBarColor),
                navigationIcon = {
                    if (hasMenuItem) {
                        IconButton(
                            onClick = {


                                scopeCoroutine.launch {
                                    Log.e("drawer", "send opene")

                                    currentDrawerState(true)


                                }


                            },

                            ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu),
                                //LocalContentAlpha.current
                                tint = Color.White,
                                contentDescription = "menu item",
                                modifier = Modifier.size(30.dp)
                            )

                        }
                    }


                },
                actions = {
                    if (hasSearchItem) {
                        IconButton(
                            onClick = {
                                onSearchIconClicked(true)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = "search icon",
                                tint = Color.White
                            )
                        }
                    }

                })


//    }

}


@Composable
fun MainListItems(
    categories: List<Category>, onCategoryClicked: (categoryName: String) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        itemsIndexed(categories) { index, category ->
            MainItem(category, index) { name ->
                onCategoryClicked(name)
            }

        }
    }
}

@Preview
@Composable
private fun MainListItemsPreview() {
    //MainListItems(categories =Data.categoriesList)
}

@Composable
fun MainItem(
    category: Category, index: Int, onCategoryCardClicked: (categoryName: String) -> Unit
) {
    Card(
        modifier = Modifier
            .height(150.dp)
            .padding(8.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 24.dp,
                    topEnd = 24.dp,
                    bottomStart = if (index % 2 != 0) 0.dp else 24.dp,
                    bottomEnd = if (index % 2 == 0) 0.dp else 24.dp
                )
            )

            .background(category.categoryBackground)
            .clickable {
                onCategoryCardClicked(category.categoryName)
            }, colors = CardDefaults.cardColors(Color.Transparent)
    ) {

        Image(
            painterResource(id = category.categoryImage),
            contentDescription = "category image",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(80.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = category.categoryName,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(5.dp),
            color = Color.White
        )

    }
}

@Preview
@Composable
private fun MainItemPreview() {
    MainItem(
        category = Category(
            categoryBackground = Color.Red,
            categoryName = "Sports",
            categoryImage = R.drawable.sports
        ), 0
    ) {

    }

}

@Preview
@Composable
private fun ToolbarHeaderPreview() {
    //ToolbarHeader("News App")
}

