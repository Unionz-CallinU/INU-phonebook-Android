package com.example.inuphonebook.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
fun DescriptionScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){

    //선택된 item
    val item = itemViewModel.selectedItem

    Scaffold(
        topBar = {
            TopBar(
                homeIcon = R.drawable.ic_launcher_foreground,
                favoriteIcon = R.drawable.ic_launcher_foreground,
            )
        }
    ){
        Column(
            modifier = Modifier.padding(it)
        ) {

        }
        //item을 기반으로 필요한 정보를 구성
    }
}

@Preview
@Composable
fun TestDescriptionScreen(){
    
}