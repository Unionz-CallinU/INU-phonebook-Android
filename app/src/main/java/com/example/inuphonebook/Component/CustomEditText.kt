package com.example.inuphonebook.Component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
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
    trailingIcon : Int?,
    onTrailingClick : () -> Unit,
    onKeyboardDone : () -> Unit,
    shape : Shape
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
            if (trailingIcon != null){
                IconButton(
                    onClick = onTrailingClick,
                    modifier = Modifier.size(25.dp),
                    content = {
                        Icon(
                            painter = painterResource(trailingIcon),
                            contentDescription = "Trailing Icon"
                        )
                    }
                )
            }
        },
        shape = shape
    )
}

@Preview
@Composable
fun TestCustomEditText(){

}