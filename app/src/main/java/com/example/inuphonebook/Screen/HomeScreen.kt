package com.example.inuphonebook.Screen

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inuphonebook.Component.CustomSpinner
import com.example.inuphonebook.Component.InfoItem
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    //category item들 초기화
    val itemList = listOf(
        "대학본부",
        "대학",
        "교수",
        "연락처"
    )

    var searchContent by remember{mutableStateOf("")}//검색 창에 검색한 값을 저장하는 변수
    val datas = itemViewModel.datas.observeAsState()
    var isSelectedTab by remember{mutableStateOf(1)} //현재 탭이 눌려있는 정보 저장
    val pagerState = rememberPagerState(isSelectedTab)

    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = R.drawable.ic_launcher_foreground,
                actionIcon = R.drawable.ic_launcher_foreground
            )
        }
    ){
        Column(
            modifier = Modifier.padding(it)
        ) {
            Spacer(Modifier.height(50.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            ){
                CustomSpinner(
                    itemList = itemList
                )
                Spacer(Modifier.width(20.dp))
                SearchBar(
                    value = searchContent,
                    onValueChange = { content ->
                        searchContent = content
                    }
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ){
                Column{
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    isSelectedTab = 1
                                },
                            text = "학과 사무실",
                            textAlign = TextAlign.Center,
                            color = if(isSelectedTab == 1) Color.Black else Color.Gray
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .clickable {
                                    isSelectedTab = 2
                                },
                            text = "교수진",
                            textAlign = TextAlign.Center,
                            color = if(isSelectedTab == 1) Color.Gray else Color.Black
                        )
                    }
                    HorizontalPager(
                        modifier = Modifier,
                        state = pagerState,
                        count = 2
                    ){page ->
                        //if를 이용해 page가 1이면 학과사무실 리스트를 가져와 LazyColumn으로 그림 & 2면 교수님리스트를 가져와 LazyColumn으로 그림 
                        LazyColumn(
                            modifier = Modifier.weight(1f)
                        ){
                            //변동된 data를 받아와 Column을 구성
                            items(datas.value!!){data ->
                                Box(
                                    modifier = Modifier,
                                ){
                                    InfoItem(
                                        item = data,
                                        onClick = {
                                            itemViewModel.setSelectedItem(data)
                                            navController.navigate(Screens.DescriptionScreen.name)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        //검색한 내용이 바뀔 떄 itemViewModel에서 데이터를 받아옴
        LaunchedEffect(searchContent){
            itemViewModel.search(searchContent)
        }

        //Text를 눌러 Page를 바꿀 때
        LaunchedEffect(isSelectedTab){
            pagerState.animateScrollToPage(isSelectedTab - 1)
        }
    }
}

@Preview
@Composable
fun TestHomeScreen(){

}