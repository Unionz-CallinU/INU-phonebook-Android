package com.example.inuphonebook

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.Screen.DescriptionScreen
import com.example.inuphonebook.Screen.EditCategoryScreen
import com.example.inuphonebook.Screen.FavoriteScreen
import com.example.inuphonebook.Screen.HomeScreen
import com.example.inuphonebook.Screen.SearchScreen
import com.example.inuphonebook.Screen.SplashScreen
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun PhoneBookApp(
    itemViewModel : ItemViewModel
){
    INUPhoneBookTheme {
        val navController = rememberNavController()
        
        //navigate 경로 정의
        NavHost(
            navController = navController,
            startDestination = Screens.SplashScreen.name
        ){
            composable(Screens.SplashScreen.name){
                SplashScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
                )
            }
            composable(Screens.HomeScreen.name){
                HomeScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
                )
            }
            composable("${Screens.DescriptionScreen.name}/{id}"){backStackEntry ->
                DescriptionScreen(
                    itemViewModel = itemViewModel,
                    navController = navController,
                    _id = backStackEntry.arguments?.getString("id") ?: throw NullPointerException("Id is NULL")
                )
            }
            composable("${Screens.SearchScreen.name}/{searchContent}"){backStackEntry ->
                SearchScreen(
                    itemViewModel = itemViewModel,
                    navController = navController,
                    _searchContent = backStackEntry.arguments?.getString("searchContent") ?: throw NullPointerException("Search Content is NULL")
                )
            }
            composable(Screens.FavoriteScreen.name){
                FavoriteScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
                )
            }
            composable(Screens.EditCategoryScreen.name){
                EditCategoryScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
                )
            }
        }
    }
}