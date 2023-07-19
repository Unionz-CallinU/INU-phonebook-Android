package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    value : String = "",
    onValueChange : (String) -> Unit,
    fontSize : TextUnit = 15.sp,
    textColor : Color = Color.Black
){
    Row(
        modifier = modifier
    ){

        CustomEditText(
            fontSize = fontSize,
            textColor = textColor,
            value = value,
            onValueChange = onValueChange
        )
    }
}

@Preview
@Composable
fun TestSearchBar(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(color = Color.White)
        ) {
            SearchBar(
                modifier = Modifier,
                onValueChange = {}
            )
        }

    }
}