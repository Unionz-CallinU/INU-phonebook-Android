package com.example.inuphonebook.Screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionScreen(){
    Scaffold(
        topBar = {
            TopBar(
                navigationIcon = R.drawable.ic_launcher_foreground,
                actionIcon = R.drawable.ic_launcher_foreground,
            )
        }
    ){
        Column(
            modifier = Modifier.padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            //서버에서 받아온 데이터를 기반으로 layout을 구성
        }
    }
}

@Preview
@Composable
fun TestDescriptionScreen(){
    
}