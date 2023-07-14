package com.example.inuphonebook.Component

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomEditText(
    modifier : Modifier = Modifier,
    value : String = "",
    onValueChange : (String) -> Unit = {},
    fontFamily : FontFamily,
    fontWeight : FontWeight,
    fontSize : TextUnit,
    textColor : Color,
    keyboardOptions : KeyboardOptions = KeyboardOptions.Default,
    placeholder : String = ""
){
    TextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        placeholder = {
            Text(
                text = placeholder,
                fontFamily = fontFamily,
                fontWeight = fontWeight,
                fontSize = fontSize,
                color = textColor
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun TestCustomEditText(){

}