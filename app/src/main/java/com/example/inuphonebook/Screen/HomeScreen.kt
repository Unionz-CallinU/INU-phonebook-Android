package com.example.inuphonebook.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    //검색 내용
    var searchContent by remember{mutableStateOf("")}
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.tmp_home,
            favoriteIcon = R.drawable.tmp_favorite
        )
        Spacer(Modifier.height(30.dp))

        Logo(
            size = 200.dp,
            logoIcon = R.drawable.ic_launcher_background
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
            trailingIcon = R.drawable.search_icon,
            onTrailingClick = {
                //itemViewModel에 검색을 시켜 놓기
                coroutineScope.launch { itemViewModel.search(searchContent) }
                navController.navigate(
                    route = "${Screens.SearchScreen.name}/$searchContent",
                )
            },
            placeHolder = "상세 정보를 입력하세요",
            onKeyboardDone = {
                navController.navigate(Screens.SearchScreen.name)
            }
        )
    }
}

@Preview
@Composable
fun TestHomeScreen(){
    INUPhoneBookTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            HomeScreen(
                navController = rememberNavController(),
                itemViewModel = ItemViewModel()
            )
        }
    }
}