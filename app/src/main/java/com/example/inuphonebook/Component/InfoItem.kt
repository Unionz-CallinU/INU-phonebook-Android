package com.example.inuphonebook.Component

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.inuphonebook.LocalDB.Employee

/** 미사용 resource */
@OptIn(ExperimentalCoilApi::class)
@Composable
fun InfoItem(
    modifier : Modifier = Modifier,
    item : Employee,
    imageSize : Dp = 30.dp,
    textSize : TextUnit = 15.sp,
    textColor : Color = Color.Black,
    onClick : () -> Unit,
){
    Row(
        modifier = modifier.clickable{
            onClick()
        },
    ){
        Image(
            modifier = Modifier.size(imageSize)
                .clip(CircleShape),
            painter = rememberImagePainter(data = item.photo),
            contentDescription = "ID Photo"
        )
    }
    Spacer(Modifier.width(10.dp))
    Column{
        Text(
            text = item.name,
            fontSize = textSize,
            color = textColor
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = item.role, //임시
            fontSize = textSize,
            color = textColor
        )
        Spacer(Modifier.height(5.dp))
        Text(
            text = item.phoneNumber,
            fontSize = textSize,
            color = textColor
        )
        Divider(thickness = 1.dp)
    }
}

@Composable
@Preview
fun TestInfoItem(){

}