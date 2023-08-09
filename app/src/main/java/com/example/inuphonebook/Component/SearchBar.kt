package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.BlueGray
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    value : String = "",
    onValueChange : (String) -> Unit,
    fontSize : TextUnit = 20.sp,
    textColor : Color = Color.Black,
    placeHolder : String,
    trailingIcon : Int,
    onTrailingClick : () -> Unit,
    onKeyboardDone : () -> Unit,
    shape : Shape = RoundedCornerShape(size = 25.dp)
){
    CustomEditText(
        modifier = modifier
            .background(color = BlueGray),
        fontSize = fontSize,
        textColor = textColor,
        value = value,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        placeholder = placeHolder,
        onKeyboardDone = onKeyboardDone,
        onTrailingClick = onTrailingClick,
        shape = shape
    )
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
                onValueChange = {},
                trailingIcon = R.drawable.search_icon,
                onTrailingClick = {},
                placeHolder = "상세 정보를 입력하세요",
                onKeyboardDone = {},
                shape = RoundedCornerShape(size = 20.dp)
            )
        }
    }
}