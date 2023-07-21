package com.example.inuphonebook.Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    Scaffold(
        topBar = {
            TopBar(
                title = "즐겨찾기",
                homeIcon = R.drawable.ic_launcher_foreground,
                favoriteIcon = R.drawable.ic_launcher_foreground
            )
        }
    ){
        LazyColumn(
            modifier = Modifier.padding(it)
        ){
            //local db에 저장된 즐겨찾기 list를 띄움
        }
    }
}

@Preview
@Composable
fun TestFavoriteScreen(){

}