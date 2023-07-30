package com.example.inuphonebook.Component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.R
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier : Modifier = Modifier,
    title : String = "",
    homeIcon : Int,
    homeIconSize : Dp,
    homeClick : () -> Unit = {},
    isFavorite : Boolean = false,
    favoriteIcon : Int?,
    favoriteClick : () -> Unit = {}
){
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = title,
                    fontSize = 20.sp
                )
            }
        },
        modifier = modifier.padding(horizontal = 10.dp),
        navigationIcon = {
            IconButton(
                modifier = Modifier.size(homeIconSize),
                onClick = homeClick
            ){
                Icon(
                    painter = painterResource(homeIcon),
                    contentDescription = "navigationIcon"
                )
            }
        },
        actions = {
            if (favoriteIcon != null){
                IconButton(
                    onClick = favoriteClick
                ){
                    Icon(
                        painter = painterResource(favoriteIcon),
                        contentDescription = "actionIcon",
                        tint = if(isFavorite) Color.Yellow else Color.Unspecified
                    )
                }
            } else {
                Spacer(Modifier.width(homeIconSize))
            }
        }
    )
}

@Preview
@Composable
fun TestTopAppBar(){

}