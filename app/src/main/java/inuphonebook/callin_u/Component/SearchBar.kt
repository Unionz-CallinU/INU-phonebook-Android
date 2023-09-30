package inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inuphonebook.ui.theme.BlueGray
import inuphonebook.ui.theme.Gray3

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
    EditText(
        modifier = modifier
            .background(
                color = if (isSystemInDarkTheme()) Gray3 else BlueGray,
                shape = shape
            ),
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