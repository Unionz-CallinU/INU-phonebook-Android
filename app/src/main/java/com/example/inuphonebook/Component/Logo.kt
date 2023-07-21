package com.example.inuphonebook.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun Logo(
    size : Dp,
    logoIcon : Int,
){
    Image(
        modifier = Modifier.size(size).clip(shape = CircleShape),
        painter = painterResource(logoIcon),
        contentDescription = "Logo Image"
    )
}