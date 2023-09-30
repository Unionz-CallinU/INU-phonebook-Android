package inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inuphonebook.R
import inuphonebook.ui.theme.Black
import inuphonebook.ui.theme.BlueGray
import inuphonebook.ui.theme.Gray3
import inuphonebook.ui.theme.INUPhoneBookTheme
import inuphonebook.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditText(
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
    fontFamily : FontFamily = FontFamily(Font(R.font.pretendard_medium)),
    shape : Shape
){
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        textStyle = TextStyle(
            fontSize = fontSize,
            fontFamily = fontFamily
        ),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {onKeyboardDone()}
        ),
        placeholder = {
            Text(
                modifier = Modifier.background(color = Color.Transparent),
                text = placeholder,
                fontSize = fontSize,
                color = textColor,
                fontFamily = fontFamily,
                letterSpacing = 1.sp
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent,
            cursorColor = if(isSystemInDarkTheme()) White else Black,
        ),
        trailingIcon = {
            if (trailingIcon != null){
                IconButton(
                    onClick = onTrailingClick,
                    modifier = Modifier
                        .size(25.dp)
                        .background(color = Color.Transparent),
                    content = {
                        Icon(
                            painter = painterResource(trailingIcon),
                            contentDescription = "Trailing Icon"
                        )
                    }
                )
            }
        },
        shape = shape,
    )
}

@Preview
@Composable
fun TestEditText(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            contentAlignment = Alignment.Center
        ){
            EditText(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .background(
                        color = if (isSystemInDarkTheme()) Gray3 else BlueGray,
                        shape = RoundedCornerShape(size = 25.dp)
                    ),
                fontSize = 20.sp,
                textColor = Black,
                trailingIcon = R.drawable.search,
                onTrailingClick = { /*TODO*/ },
                onKeyboardDone = { /*TODO*/ },
                shape = RoundedCornerShape(size = 25.dp)
            )
        }
    }
}