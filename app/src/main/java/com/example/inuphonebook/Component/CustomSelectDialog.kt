package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun CustomSelectDialog(
    modifier : Modifier = Modifier,
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    categoryList : MutableList<FavCategory>,
    title : String = "title",
    message : String = "msg",
    cancelMsg : String = "cancelMsg",
    okMsg : String = "okMsg",
    onOkClick : () -> Unit = {},
    width : Dp,
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ){
        Column(
            modifier = modifier
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(10.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                color = Black,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = message,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                color = Black,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(horizontal = 10.dp)
            ){
                CustomSpinner(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    itemList = categoryList,
                    width = width - 60.dp
                )
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.weight(1f)
                        .clickable{
                            onDismissRequest()
                        },
                    textAlign = TextAlign.Center,
                    text = cancelMsg,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Black,
                    letterSpacing = 1.sp
                )
                Text(
                    modifier = Modifier.weight(1f)
                        .clickable{
                            onOkClick()
                        },
                    textAlign = TextAlign.Center,
                    text = okMsg,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Black,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun TestCustomInputDialog(){
    INUPhoneBookTheme {
        Column(
            modifier = Modifier
                .width(300.dp)
                .height(165.dp)
                .background(color = Color.White),
        ){
            CustomSelectDialog(
                categoryList = mutableListOf(),
                onDismissRequest = {},
                width = 300.dp
            )
        }
    }
}