package com.example.inuphonebook.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.inuphonebook.Component.Logo
import com.example.inuphonebook.Component.TopBar
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
    val favoriteProfessors = itemViewModel.favProfessorDatas.observeAsState()
    val favoriteEmployees = itemViewModel.favEmployeeDatas.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "즐겨찾기",
            homeIcon = R.drawable.tmp_home,
            homeIconSize = 20.dp,
            favoriteIcon = null
        )
        Spacer(Modifier.height(17.dp))

        Log.d("BBBBBB","favEmployee size : ${favoriteEmployees.value?.size}, favProfessor size : ${favoriteProfessors.value?.size}")
        //if (favorite List가 존재하지 않는다면)
        if (favoriteEmployees.value?.size == 0 && favoriteProfessors.value?.size == 0){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Logo(
                    size = 50.dp,
                    logoIcon = R.drawable.ic_launcher_background
                )
            }

        } else {
            //if(학과 사무실 정보의 list.size가 0이 아니라면)
            if(favoriteEmployees.value?.size != 0){
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
                LazyColumn{
                    items(favoriteEmployees.value!!){employee ->
                        ListItem(
                            item = employee,
                            onClick = {
                                itemViewModel.setSelectedItem(employee)
                                navController.navigate(Screens.DescriptionScreen.name)
                            },
                            onFavoriteClick = {
                                //LocalDB의 Favorite List를 저장
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(15.dp))

            //if(교수진 정보의 list.size가 0이 아니라면)
            if(favoriteProfessors.value?.size != 0){
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
                LazyColumn{
                    items(favoriteProfessors.value!!){professor ->
                        ListItem(
                            item = professor,
                            onClick = {
                                itemViewModel.setSelectedItem(professor)
                                navController.navigate(Screens.DescriptionScreen.name)
                            },
                            onFavoriteClick = {
                                itemViewModel.updateProfessor(professor)
                            }
                        )
                    }
                }
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