package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
    title : String = "",
    subTitle : String = "",
    cancelMsg : String = "",
    okMsg : String = ""
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties
    ){
        Column(
            modifier = Modifier
                .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                .width(252.dp).height(139.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(30.dp))

            Text(
                text = title,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = subTitle,
                fontSize = 12.sp
            )

            Spacer(Modifier.height(10.dp))

            Divider(thickness = 1.dp)

            Row(
                modifier = Modifier.height(52.dp).fillMaxWidth(),
            ){
                Text(
                    modifier = Modifier.weight(1f)
                        .padding(vertical = 11.dp),
                    text = cancelMsg,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier.weight(1f)
                        .padding(vertical = 11.dp),
                    text = okMsg,
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun TestDialog(){
    INUPhoneBookTheme {
        CustomAlertDialog(
            onDismissRequest = {},
            title = "즐겨찾기",
            subTitle = "즐겨찾기 목록에 추가하시겠습니까?",
            cancelMsg = "취소",
            okMsg = "확인"
        )
    }
}