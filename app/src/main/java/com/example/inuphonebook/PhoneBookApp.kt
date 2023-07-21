package com.example.inuphonebook

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Model.Item
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.Screen.DescriptionScreen
import com.example.inuphonebook.Screen.FavoriteScreen
import com.example.inuphonebook.Screen.HomeScreen
import com.example.inuphonebook.Screen.SearchScreen
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun PhoneBookApp(
    itemViewModel : ItemViewModel
){
    INUPhoneBookTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name
        ){
            composable(Screens.HomeScreen.name){
                HomeScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
                )
            }
            composable("${Screens.DescriptionScreen.name}/{item}"){
                DescriptionScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
                )
            }
            composable("${Screens.SearchScreen.name}/{searchContent}"){backStackEntry ->
                SearchScreen(
                    itemViewModel = itemViewModel,
                    navController = navController,
                    backStackEntry.arguments?.getString("searchContent")
                )
            }
            composable(Screens.FavoriteScreen.name){
                FavoriteScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
                )
            }
        }
    }
}

@Preview
@Composable
fun TestAppUI(){

}