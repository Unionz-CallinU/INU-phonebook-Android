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
    navigationIcon : Int,
    navigationIconClick : () -> Unit = {},
    actionIcon : Int,
    actionIconClick : () -> Unit = {}
){
    TopAppBar(
        title = {Text(title)},
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = navigationIconClick
            ){
                Icon(
                    painter = painterResource(navigationIcon),
                    contentDescription = "navigationIcon"
                )
            }
        },
        actions = {
            IconButton(
                onClick = actionIconClick
            ){
                Icon(
                    painter = painterResource(actionIcon),
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