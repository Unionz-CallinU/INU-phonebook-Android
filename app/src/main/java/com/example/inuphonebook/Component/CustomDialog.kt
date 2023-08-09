package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.Blue
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import com.example.inuphonebook.ui.theme.White

@Composable
fun CustomAlertDialog(
    modifier : Modifier = Modifier,
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    highlightText : String = "",
    baseText : String = "",
    okMsg : String = "",
    onOkClick : () -> Unit,
){
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontSize = 20.sp)){
            append(highlightText)
        }
        withStyle(style = SpanStyle(fontSize = 14.sp)){
            append(baseText)
        }
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ){
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(30.dp))

            Text(
                text = text,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                color = Black,
                letterSpacing = 1.sp
            )

            Spacer(Modifier.height(20.dp))

            Divider(thickness = 1.dp)

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable{
                        onOkClick()
                    }
                    .background(color = Blue)
            ){
                Text(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = okMsg,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    textAlign = TextAlign.Center,
                    color = White,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun TestDialog(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier.width(250.dp).height(100.dp)
        ){
            CustomAlertDialog(
                onDismissRequest = {},
                highlightText = "***님이",
                baseText = "\n 즐겨찾기 목록에 추가되었습니다",
                okMsg = "확인",
                onOkClick = {}
            )
        }
    }
}