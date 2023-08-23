package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.DarkModeBackground
import com.example.inuphonebook.ui.theme.Gray2
import com.example.inuphonebook.ui.theme.Gray3
import com.example.inuphonebook.ui.theme.Gray4
import com.example.inuphonebook.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier : Modifier = Modifier,
    title : String = "",
    homeIcon : Int,
    homeClick : () -> Unit = {},
    isFavorite : Boolean = false,
    favoriteIcon : Int?,
    favoriteClick : () -> Unit = {}
){
    //배경 색
    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = if (isSystemInDarkTheme()) White else Black,
                    letterSpacing = 1.sp
                )
            }
        },
        modifier = modifier.padding(horizontal = 20.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
        ),
        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .size(24.dp),
                onClick = homeClick
            ){
                Icon(
                    painter = painterResource(homeIcon),
                    contentDescription = "navigationIcon",
                    tint = if(isSystemInDarkTheme()) Gray2 else Color.Unspecified
                )
            }
        },
        actions = {
            if (favoriteIcon != null){
                IconButton(
                    modifier = Modifier
                        .size(24.dp),
                    onClick = favoriteClick
                ){
                    Icon(
                        painter = painterResource(favoriteIcon),
                        contentDescription = "actionIcon",
                        tint = if(isFavorite) Color.Yellow else if(isSystemInDarkTheme()) Gray2 else Color.Unspecified
                    )
                }
            } else {
                Spacer(Modifier.width(24.dp))
            }
        }
    )
}