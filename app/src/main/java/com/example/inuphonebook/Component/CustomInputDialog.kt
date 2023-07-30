package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import java.util.Properties

@Composable
fun CustomInputDialog(
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    categoryList : List<String>,
    title : String = "title",
    message : String = "msg",
    cancelMsg : String = "cancelMsg",
    okMsg : String = "okMsg",
){
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(10.dp))
            Text(
                text = title,
                fontSize = 20.sp
            )
            Spacer(Modifier.height(5.dp))
            Text(
                text = message,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.padding(horizontal = 10.dp)
            ){
                CustomSpinner(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    itemList = categoryList
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
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = cancelMsg,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    text = okMsg,
                    fontSize = 20.sp
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
            CustomInputDialog(
                categoryList = listOf("기본"),
                onDismissRequest = {},
            )
        }
    }
}