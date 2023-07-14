package com.example.inuphonebook.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.Item
import com.example.inuphonebook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    datas : LiveData<List<Item>> //서버에서 전체 list를 받아옴
){
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
            SearchBar()

            Spacer(Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
                    .padding(10.dp)
            ){
                //AcitivtyMain에서 받아온 datas를 바탕으로 lazyColumn을 동적 구성
            }
        }
    }
}

@Preview
@Composable
fun TestHomeScreen(){

}