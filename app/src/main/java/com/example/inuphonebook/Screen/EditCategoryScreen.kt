package com.example.inuphonebook.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun EditCategoryScreen(){
    val categoryList = listOf("기본") //itemViewModel의 Category List를 받아옴
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        TopBar(
            homeIcon = R.drawable.tmp_home,
            homeIconSize = 40.dp,
            favoriteIcon = null,
            title = "즐겨찾기 편집"
        )
        Spacer(Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp),
            horizontalArrangement = Arrangement.End
        ){
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(R.drawable.plus_btn),
                    contentDescription = "Add Category"
                )
            }
            Spacer(Modifier.width(5.dp))
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    painter = painterResource(R.drawable.minus_btn),
                    contentDescription = "Delete Category"
                )
            }
        }
        Spacer(Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 5.dp,
                alignment = Alignment.CenterVertically
            )
        ){
            items(categoryList) {category ->
                var isSelected by remember{mutableStateOf(true)}
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .size(25.dp)
                        .background(color = FillNotFavoriteColor)
                ){
                    IconButton(
                        onClick = {
                            isSelected = !isSelected
                        })
                    {
                        Icon(
                            modifier = Modifier.clip(shape = CircleShape),                            painter = if (isSelected) painterResource(R.drawable.check_btn) else painterResource(R.drawable.non_check_btn),
                            contentDescription = "Check Box",
                            tint = Color.Unspecified
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Text(
                        modifier = Modifier
                            .fillMaxSize(),
                        text = category,
                        fontSize = 20.sp,
                        color = Color.Blue,
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TestEditCategoryScreen(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            EditCategoryScreen()
        }
    }
}