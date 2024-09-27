package com.example.newsapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column



import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newsapp.ui.theme.borderColor
import com.example.newsapp.ui.theme.textColor

@Composable
fun SettingsScreen(languageChange:(String)->Unit,onMenuItemClicked: (Boolean) -> Unit = {}) {
    Column()
    {
        ToolbarHeader(title = stringResource(R.string.settings), hasMenuItem = true)
        {
            onMenuItemClicked(it)
        }
        Spacer(modifier = Modifier.height(150.dp))
        Text(
            stringResource(R.string.language),
            fontWeight = FontWeight.Bold,
            modifier= Modifier.padding(start= 10.dp))
        DropDownMenu()
        {
            language->
                languageChange(language)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(languageChange:(String)->Unit) {

    var isDropDownExpanded by remember {
        mutableStateOf(false)
    }
    var itemPosition by remember {
        mutableIntStateOf(0)
    }



    var languageChoosen by remember {
        mutableStateOf("")
    }


    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.White)
               ,


            ) {
           OutlinedTextField(
               value = languageChoosen,
               onValueChange = {},
                placeholder = {
                              Text(text = stringResource(R.string.choose_langauge))
                },
               readOnly = true,
               trailingIcon = {
                   Image(
                       painter = painterResource(id = R.drawable.ic_downarrow),
                       contentDescription = "icon"
                       ,modifier= Modifier
                           .align(Alignment.CenterEnd)
                           .clickable { isDropDownExpanded = true }
                   )
               },

               colors = TextFieldDefaults.outlinedTextFieldColors(
                   containerColor = Color.White,
                   unfocusedBorderColor = borderColor,
                   focusedBorderColor = borderColor
               ),
            textStyle = TextStyle(
                color = textColor
            )
            

           )



            DropdownMenu(
                expanded = isDropDownExpanded,
                onDismissRequest = { isDropDownExpanded = false }) {

                Data.languages.forEachIndexed { index, language ->
                    DropdownMenuItem(text = {
                        Text(text = language)
                    },
                        onClick = {
                            isDropDownExpanded = false
                            itemPosition = index
                            languageChoosen =  language
                            languageChange(language)
                        })
                }
            }

        }
    }

}

@Preview
@Composable
private fun SettingsScreenPreview() {
//    DropDownMenu()
    NavigationDrawer(navigateToDrawerScreen = {

    }) {
        SettingsScreen(languageChange = {

        })
        it(true)

    }

}

