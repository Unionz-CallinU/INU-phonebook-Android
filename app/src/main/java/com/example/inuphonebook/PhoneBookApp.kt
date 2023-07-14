package com.example.inuphonebook

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Model.Item
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.Screen.DescriptionScreen
import com.example.inuphonebook.Screen.FavoriteScreen
import com.example.inuphonebook.Screen.HomeScreen
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun PhoneBookApp(){
    INUPhoneBookTheme {
        val navController = rememberNavController()

        //서버에서 받아온 데이터
        val datas = MutableLiveData<List<Item>>()

        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name
        ){
            composable(Screens.HomeScreen.name){
                HomeScreen(datas)
            }
            composable(Screens.DescriptionScreen.name){
                DescriptionScreen()
            }
            composable(Screens.FavoriteScreen.name){
                FavoriteScreen()
            }
        }
    }
}

@Preview
@Composable
fun TestAppUI(){

}