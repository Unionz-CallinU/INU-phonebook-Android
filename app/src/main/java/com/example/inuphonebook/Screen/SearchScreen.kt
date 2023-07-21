package com.example.inuphonebook.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun SearchScreen(
    itemViewModel : ItemViewModel,
    navController : NavController,
){

}

@Composable
@Preview
fun TestSearchScreen(){
    INUPhoneBookTheme {
        SearchScreen(
            itemViewModel = ItemViewModel(),
            navController = rememberNavController()
        )
    }
}