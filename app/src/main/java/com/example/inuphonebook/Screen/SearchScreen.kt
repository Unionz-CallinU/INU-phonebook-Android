package com.example.inuphonebook.Screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.ListItem
import com.example.inuphonebook.Component.Logo
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun SearchScreen(
    itemViewModel : ItemViewModel,
    navController : NavController,
    _searchContent : String,
){
    val context = LocalContext.current
    //검색 내용 저장
    var searchContent by remember{mutableStateOf(_searchContent)}

    val employeeDatas = itemViewModel.employeeDatas.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.home,
            homeClick = {
                navController.navigate(Screens.HomeScreen.name)
            },
            homeIconSize = 40.dp,
            favoriteIcon = R.drawable.favorite,
            favoriteClick = {
                navController.navigate(Screens.FavoriteScreen.name)
            }
        )

        Spacer(Modifier.height(25.dp))

        Text(
            text = searchContent,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(17.dp))

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 37.5.dp),
            value = searchContent,
            onValueChange = {content ->
                searchContent = content
            },
            onKeyboardDone = {
//                if (searchContent == ""){
//                    showToast(
//                        context = context,
//                        msg = "검색 내용을 입력해주세요"
//                    )
//                } else {
//                    val resultMsg = itemViewModel.search(searchContent)
//                    if (resultMsg != "Success" && resultMsg != "Result is NULL"){
//                        showToast(context, resultMsg)
//                    }
//                }
            },
            placeHolder = "상세 정보를 입력하세요",
            trailingIcon = R.drawable.search_icon,
            onTrailingClick = {
//                if (searchContent == ""){
//                    showToast(
//                        context = context,
//                        msg = "검색 내용을 입력해주세요"
//                    )
//                } else {
//                    val resultMsg = itemViewModel.search(searchContent)
//                    if (resultMsg != "Success" && resultMsg != "Result is NULL"){
//                        showToast(context, resultMsg)
//                    }
//                }
            }
        )

        Spacer(Modifier.height(17.dp))

        //if (employeeDatas의 데이터가 비어있으면 로고만 띄워놓기)
        if (employeeDatas.value?.size == 0){
            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Logo(
                        height = 100.dp,
                        width = 76.dp,
                        logoIcon = R.drawable.main_logo,
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    Spacer(Modifier.height(15.dp))
                    Text(
                        text = "검색 결과가 없습니다",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
        } else {
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
                items(employeeDatas.value!!){employee ->
                    ListItem(
                        employee = employee,
                        onClick = {
                            itemViewModel.setSelectedItem(employee)
                            navController.navigate(Screens.DescriptionScreen.name)
                        },
                        onFavoriteClick = {
                            //LocalDB의 Favorite List를 저장
                            itemViewModel.insertEmployee(employee)
                        }
                    )
                }
            }
        }
    }
}

private fun showToast(context : Context, msg : String,){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Composable
@Preview
fun TestSearchScreen(){
    INUPhoneBookTheme {
        SearchScreen(
            itemViewModel = ItemViewModel(LocalContext.current),
            navController = rememberNavController(),
            _searchContent = "정보 대학"
        )
    }
}