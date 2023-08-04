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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun CustomAlertDialog(
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    mainMsg : String = "",
    okMsg : String = "",
    onOkClick : () -> Unit,
){
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
                text = mainMsg,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Divider(thickness = 1.dp)

            Row(
                modifier = Modifier.fillMaxWidth()
                    .clickable{
                        onOkClick()
                    }
                    .background(color = Color.Blue)
            ){
                Text(
                    modifier = Modifier.fillMaxWidth().weight(1f),
                    text = okMsg,
                    textAlign = TextAlign.Center,
                    color = Color.White
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
                mainMsg = "***님이 \n 즐겨찾기 목록에 추가되었습니다",
                okMsg = "확인",
                onOkClick = {}
            )
        }
    }
}