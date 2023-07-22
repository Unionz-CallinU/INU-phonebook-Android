package com.example.inuphonebook.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.ListItem
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.Item
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun FavoriteScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    //localDB내 favorite 리스트
    val favoriteProfessors = listOf<Item>()
    val favoriteEmployees = listOf<Item>()

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.White)
    ){
        TopBar(
            homeIcon = R.drawable.tmp_home,
            favoriteIcon = R.drawable.tmp_favorite
        )
        Spacer(Modifier.height(17.dp))

        //if(학과 사무실 정보의 list.size가 0이 아니라면)
        if(favoriteEmployees.size != 0){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .background(color = FillNotFavoriteColor),
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.width(20.dp))
                Text(
                    text = "학과 사무실",
                    fontSize = 20.sp
                )
            }
        }

        LazyColumn{
            items(favoriteEmployees){headquarter ->
                ListItem(
                    item = headquarter,
                    onClick = {
                        itemViewModel.setSelectedItem(headquarter)
                        navController.navigate(Screens.DescriptionScreen.name)
                    },
                    onFavoriteClick = {
                        //LocalDB의 Favorite List를 저장
                    }
                )
            }
        }

        Spacer(Modifier.height(15.dp))

        //if(교수진 정보의 list.size가 0이 아니라면)
        if(favoriteProfessors.size != 0){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .background(color = FillNotFavoriteColor),
                verticalAlignment = Alignment.CenterVertically
            ){
                Spacer(Modifier.width(20.dp))
                Text(
                    text = "교수",
                    fontSize = 20.sp
                )
            }
        }

        LazyColumn{
            items(favoriteProfessors){professor ->
                ListItem(
                    item = professor,
                    onClick = {
                        itemViewModel.setSelectedItem(professor)
                        navController.navigate(Screens.DescriptionScreen.name)
                    },
                    onFavoriteClick = {

                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun TestFavoriteScreen(){
    INUPhoneBookTheme {
        Box(
            Modifier.fillMaxSize()
        ){
            FavoriteScreen(
                navController = rememberNavController(),
                itemViewModel = ItemViewModel(LocalContext.current)
            )
        }
    }
}