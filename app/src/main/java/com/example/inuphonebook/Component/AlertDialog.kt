package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.Blue
import com.example.inuphonebook.ui.theme.Gray4
import com.example.inuphonebook.ui.theme.White

@Composable
fun AlertDialog(
    modifier : Modifier = Modifier,
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    highlightText : String = "",
    baseText : String = "",
    okMsg : String = "",
    onOkClick : () -> Unit,
    fontFamily : FontFamily = FontFamily(Font(R.font.pretendard_medium))
){
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 20.sp)){
            append(highlightText)
        }
        withStyle(style = SpanStyle(fontSize = 14.sp)){
            append(baseText)
        }
    }
    val textColor = if(isSystemInDarkTheme()) White else Black
    val dialogBackground = if(isSystemInDarkTheme()) Gray4 else White

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ){
        Column(
            modifier = modifier
                .background(
                    color = dialogBackground,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .clip(shape = RoundedCornerShape(size = 10.dp)),
        ){
            Column(
                modifier = Modifier.weight(2f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Spacer(Modifier.weight(3f))

                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    fontFamily = fontFamily,
                    color = textColor,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.weight(2f))
            }
            Divider(thickness = 1.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onOkClick()
                    }
                    .background(color = Blue)
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = okMsg,
                    fontSize = 20.sp,
                    fontFamily = fontFamily,
                    textAlign = TextAlign.Center,
                    color = White,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}