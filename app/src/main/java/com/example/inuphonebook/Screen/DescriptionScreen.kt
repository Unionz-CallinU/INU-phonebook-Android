package com.example.inuphonebook.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DescriptionScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    //선택된 item
    val item = itemViewModel.selectedItem.value ?: throw NullPointerException("target is NULL")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.tmp_home,
            favoriteIcon = R.drawable.tmp_favorite,
        )
        Spacer(Modifier.height(50.dp))

        Icon(
            modifier = Modifier.clip(shape = CircleShape),
            painter = painterResource(item.image),
            contentDescription = "Icon",
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 25.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ){
                Text(
                    text = "교수명",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "교수명",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "전화번호",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "주전공",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "담당과목",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "연구실",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "이메일",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "홈페이지",
                    fontSize = 20.sp
                )
            }
            Column(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxHeight()
            ){

                Text(
                    text = item.name,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = item.phone,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = item.department,
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "담당과목",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "연구실",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "이메일",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "홈페이지?",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "교수명",
                    fontSize = 20.sp
                )

                Spacer(Modifier.height(5.dp))

            }
        }
    }
}

@Preview
@Composable
fun TestDescriptionScreen(){
    INUPhoneBookTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            DescriptionScreen(
                navController = rememberNavController(),
                itemViewModel = ItemViewModel()
            )
        }
    }
}