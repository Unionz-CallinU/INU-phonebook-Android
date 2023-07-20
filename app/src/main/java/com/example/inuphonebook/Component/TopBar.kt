package com.example.inuphonebook.Component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import org.w3c.dom.Text

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier : Modifier = Modifier,
    title : String = "",
    homeIcon : Int,
    homeClick : () -> Unit = {},
    favoriteIcon : Int,
    favoriteClick : () -> Unit = {}
){
    TopAppBar(
        title = {Text(title)},
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = homeClick
            ){
                Icon(
                    painter = painterResource(homeIcon),
                    contentDescription = "navigationIcon"
                )
            }
        },
        actions = {
            IconButton(
                onClick = favoriteClick
            ){
                Icon(
                    painter = painterResource(favoriteIcon),
                    contentDescription = "actionIcon"
                )
            }
        }
    )
}

@Preview
@Composable
fun TestTopAppBar(){

}