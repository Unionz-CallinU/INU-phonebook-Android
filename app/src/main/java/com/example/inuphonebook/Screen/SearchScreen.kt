package com.example.inuphonebook.Screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun SearchScreen(
    itemViewModel : ItemViewModel
){

}

@Composable
@Preview
fun TestSearchScreen(){
    INUPhoneBookTheme {
        SearchScreen(itemViewModel = ItemViewModel())
    }
}