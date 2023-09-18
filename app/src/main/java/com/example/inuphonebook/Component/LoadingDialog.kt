package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import com.example.inuphonebook.ui.theme.White
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Delayed

@Composable
fun LoadingDialog(
    onDismissRequest : () -> Unit,
    width : Dp,
    height : Dp,
){
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .background(color = LightGray)
                .height(height)
                .width(width)
                .padding(vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Loading",
                fontSize = 14.sp
            )
            Spacer(Modifier.height(5.dp))
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = DarkGray,
            )
        }
    }
}

@Preview
@Composable
fun TestLoadingDialog(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White)
        ){
            var isLoading by remember{mutableStateOf(true)}
            val configuration = LocalConfiguration.current
            val width = configuration.screenWidthDp
            val height = configuration.screenHeightDp
            val coroutineScope = rememberCoroutineScope()
            if (isLoading){
                LoadingDialog(
                    onDismissRequest = {
                        coroutineScope.launch{
                            delay(2000)
                            isLoading = false
                        }
                    },
                    width = (width / 5).dp,
                    height = ((height / 30)*2).dp
                )
            }
        }
    }
}