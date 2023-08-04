package com.example.inuphonebook.Screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    itemViewModel : ItemViewModel,
    navController : NavController
){

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val videoPath = "android.resource://${context.packageName}/${R.raw.splash_video}"
    val mediaItem = MediaItem.Builder().setUri(Uri.parse(videoPath)).build()

    val mExoPlayer = remember(context){
        ExoPlayer.Builder(context).build().apply{
            setMediaItem(mediaItem)
            playWhenReady = true
            prepare()
            volume = 0f
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        AndroidView(
            factory = {
                StyledPlayerView(it).apply{
                    player = mExoPlayer
                    useController = false
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                }
            }
        )
    }

    LaunchedEffect(Unit){
        coroutineScope.launch(Dispatchers.Main){
            itemViewModel.fetchFavEmployee()
            itemViewModel.fetchAllCategory()

            delay(1600)

            navController.navigate(Screens.HomeScreen.name)
        }
    }
}

@Preview
@Composable
fun TestSplashScreen(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            SplashScreen(
                itemViewModel = ItemViewModel(LocalContext.current),
                navController = rememberNavController()
            )
        }
    }
}