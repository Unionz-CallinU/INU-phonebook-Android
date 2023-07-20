package com.example.inuphonebook.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.Logo
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    //검색 내용
    var searchContent by remember{mutableStateOf("")}

    Scaffold(
        topBar = {
            TopBar(
                homeIcon = 0,
                favoriteIcon = 0
            )
        }
    ){
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(30.dp))

            Logo(
                size = 200.dp,
                logoIcon = 0
            )

            Spacer(Modifier.height(55.dp))

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 37.5.dp),
                value = searchContent,
                onValueChange = {content ->
                    searchContent = content
                },
                fontSize = 20.sp,
                trailingIcon = R.drawable.search_icon,
                placeHolder = "상세 정보를 입력하세요",
                onKeyboardDone = {
                    navController.navigate(Screens.SearchScreen.name)
                }
            )
        }
    }
}

@Preview
@Composable
fun TestHomeScreen(){
    INUPhoneBookTheme {
        HomeScreen(
            navController = rememberNavController(),
            itemViewModel = ItemViewModel()
        )
    }
}