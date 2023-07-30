package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.BlueGray
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun CustomSpinner(
    modifier : Modifier = Modifier,
    itemList : List<String> = listOf("기본"),
){
    var selectedItem by remember{mutableStateOf(itemList[0])}//itemList의 선택 값 기억
    var isOpen by remember{mutableStateOf(false)} //spinner의 상태

    Box(
        modifier = modifier.fillMaxWidth()
            .background(color = BlueGray),
    ){
        Column{
            Row(
                modifier = Modifier.padding(start = 30.dp, end = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.weight(1f),
                    text = selectedItem,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
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
                        contentDescription = "Spinner Button"
                    )
                }
            }
            DropdownMenu(
                expanded = isOpen,
                onDismissRequest = {
                    isOpen = !isOpen
                }
            ){
                itemList.forEach{item ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItem = item
                            isOpen = !isOpen
                        },
                        text = {
                            Text(
                                text = item
                            )
                        }
                    )
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
            Modifier.fillMaxSize().background(color = Color.White)
        ){
            CustomSpinner()
        }
    }
}