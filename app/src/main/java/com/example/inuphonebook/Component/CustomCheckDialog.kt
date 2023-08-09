package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.Blue
import com.example.inuphonebook.ui.theme.White

@Composable
fun CustomCheckDialog(
    modifier : Modifier = Modifier,
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    newCategory : String = "",
    msg : String = "",
    okMsg : String = "",
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ){
        Column(
            modifier = modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .clip(shape = RoundedCornerShape(size = 10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Column(
                modifier = Modifier.fillMaxWidth().weight(2f)
            ){
                Spacer(Modifier.weight(1f))

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = newCategory,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Black,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = msg,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Black,
                    textAlign = TextAlign.Center,
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.weight(1f))
            }

            Row(
                modifier = Modifier
                    .background(color = Blue)
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable{
                        onDismissRequest()
                    },
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = okMsg,
                    fontSize = 20.sp,
                    color = White,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    letterSpacing = 1.sp,
                )
            }
        }
    }
}