package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.BlueGray
import com.example.inuphonebook.ui.theme.Gray2
import com.example.inuphonebook.ui.theme.Gray3
import com.example.inuphonebook.ui.theme.Gray4
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun CustomSpinner(
    modifier : Modifier = Modifier,
    itemList : List<FavCategory> = listOf(),
    width : Dp,
){
    var selectedItem by remember{mutableStateOf(itemList[0])}//itemList의 선택 값 기억
    var isOpen by remember{mutableStateOf(false)} //spinner의 상태

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = BlueGray),
    ){
        Column{
            Row(
                modifier = Modifier
                    .padding(start = 30.dp, end = 15.dp)
                    .clickable {
                        isOpen = !isOpen
                    },
                verticalAlignment = Alignment.CenterVertically,
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = selectedItem.category,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = if(isSystemInDarkTheme()) Gray4 else Gray3
                )
                Spacer(Modifier.width(5.dp))
                IconButton(
                    modifier = Modifier.size(10.dp),
                    //spinner open
                    onClick = {
                        isOpen = !isOpen
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.spinner_dropdown_btn),
                        contentDescription = "Spinner Button",
                        tint = if(isSystemInDarkTheme()) Gray2 else Color.Unspecified
                    )
                }
            }
            DropdownMenu(
                modifier = Modifier
                    .width(width),
                expanded = isOpen,
                onDismissRequest = {
                    isOpen = !isOpen
                }
            ){
                itemList.forEachIndexed { index, item ->
                    DropdownMenuItem(
                        modifier = Modifier
                            .height(30.dp),
                        onClick = {
                            selectedItem = item
                            isOpen = !isOpen
                        },
                        text = {
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = item.category,
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                letterSpacing = 1.sp
                            )
                        }
                    )
                    if (index != itemList.size - 1){
                        Divider(thickness = 1.dp)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun TestCustomSpinner(){
    INUPhoneBookTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            CustomSpinner(
                width = LocalConfiguration.current.screenWidthDp.dp - 50.dp
            )
        }
    }
}