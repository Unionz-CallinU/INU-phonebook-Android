package com.example.inuphonebook.Screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.ListItem
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.DividerLineColor
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    itemViewModel : ItemViewModel,
    navController : NavController,
    _searchContent : String?,
){
    //검색 내용 저장
    var searchContent by remember{mutableStateOf(_searchContent)}
    val coroutineScope = rememberCoroutineScope()

    val employeeDatas = itemViewModel.employeeDatas.observeAsState()
    val professorList = itemViewModel.professorDatas.observeAsState()

    if (searchContent == null){
        throw NullPointerException("Error : SearchContent is NULL")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.tmp_home,
            favoriteIcon = R.drawable.tmp_favorite,
            favoriteClick = {
                itemViewModel.getAllEmployee()
                itemViewModel.getAllProfessor()
            }
        )

        Spacer(Modifier.height(25.dp))

        Text(
            text = searchContent!!,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(17.dp))

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 37.5.dp),
            value = searchContent!!,
            onValueChange = {content ->
                searchContent = content
            },
            onKeyboardDone = {
                coroutineScope.launch{
                    itemViewModel.search(searchContent!!)
                }
            },
            placeHolder = "상세 정보를 입력하세요",
            trailingIcon = R.drawable.search_icon,
            onTrailingClick = {
                coroutineScope.launch{
                    itemViewModel.search(searchContent!!)
                }
            }
        )

        Spacer(Modifier.height(17.dp))

        //if(학과 사무실 정보의 list.size가 0이 아니라면)
        if(employeeDatas.value?.size != 0){
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
            items(employeeDatas.value!!){employee ->
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

        Spacer(Modifier.height(15.dp))

        //if(교수진 정보의 list.size가 0이 아니라면)
        if(professorList.value?.size != 0){
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
            items(professorList.value!!){professor ->
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