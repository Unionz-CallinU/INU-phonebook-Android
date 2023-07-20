package com.example.inuphonebook.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomEditText(
    modifier : Modifier = Modifier,
    value : String = "",
    onValueChange : (String) -> Unit = {},
    fontSize : TextUnit,
    textColor : Color,
    keyboardOptions : KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    placeholder : String = "",
    trailingIcon : Int,
    onKeyboardDone : () -> Unit,
){
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {onKeyboardDone()}
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = fontSize,
                color = textColor,
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent
        ),
        trailingIcon = {
            Image(
                modifier = Modifier.size(25.dp),
                painter = painterResource(trailingIcon),
                contentDescription = "Trailing Icon"
            )
        }
    )
}

@Preview
@Composable
fun TestCustomEditText(){

}