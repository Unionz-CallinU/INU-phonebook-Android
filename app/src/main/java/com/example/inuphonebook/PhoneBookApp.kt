package com.example.inuphonebook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun PhoneBookApp(
    itemViewModel : ItemViewModel
){
    INUPhoneBookTheme {
        val navController = rememberNavController()
        itemViewModel.fetchFavEmployee()
        itemViewModel.fetchAllCategory()
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
            composable(Screens.DescriptionScreen.name){
                DescriptionScreen(
                    itemViewModel = itemViewModel,
                    navController = navController
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
                    itemViewModel = itemViewModel
                )
            }
        }
    }
}

@Preview
@Composable
fun TestAppUI(){
    INUPhoneBookTheme {
        val navController = rememberNavController()
        val itemVM = ItemViewModel(LocalContext.current)
        val itemViewModel = remember{mutableStateOf(itemVM)}
        itemViewModel.value.fetchFavEmployee()
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name
        ){
            composable(Screens.HomeScreen.name){
                HomeScreen(
                    itemViewModel = itemViewModel.value,
                    navController = navController
                )
            }
            composable(Screens.DescriptionScreen.name){
                DescriptionScreen(
                    itemViewModel = itemViewModel.value,
                    navController = navController
                )
            }
            composable("${Screens.SearchScreen.name}/{searchContent}"){backStackEntry ->
                SearchScreen(
                    itemViewModel = itemViewModel.value,
                    navController = navController,
                    _searchContent = backStackEntry.arguments?.getString("searchContent") ?: throw NullPointerException("Search Content is NULL")
                )
            }
            composable(Screens.FavoriteScreen.name){
                FavoriteScreen(
                    itemViewModel = itemViewModel.value,
                    navController = navController
                )
            }
        }
        Box(
            modifier = Modifier.fillMaxSize().background(color = Color.White)
        ){
            PhoneBookApp(
                itemViewModel = itemViewModel.value
            )
        }
    }
}