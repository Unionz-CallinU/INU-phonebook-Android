package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.BlueGray

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
    Box(
        modifier = modifier,
    ){
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
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
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    letterSpacing = 1.sp
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent
            ),
            trailingIcon = {
                if (trailingIcon != null){
                    IconButton(
                        onClick = onTrailingClick,
                        modifier = Modifier.size(25.dp)
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
            /** shadow 처리가 필요함 */
//            modifier = Modifier.graphicsLayer (
//                shadowElevation = 0.1f,
//                clip = true,
//                ambientShadowColor = Color(0x40000000),
//                spotShadowColor = Color(0x40000000),
//                shape = RoundedCornerShape(size = 25.dp)
//            )
        )
    }
}

@Preview
@Composable
fun TestCustomEditText(){

}