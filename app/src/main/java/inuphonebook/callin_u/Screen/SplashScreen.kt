package inuphonebook.Screen

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import inuphonebook.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView
import inuphonebook.Model.ItemViewModel
import inuphonebook.Model.Screens
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
            repeatMode = ExoPlayer.REPEAT_MODE_OFF
        }
    }

    //video 렌더링 전 준비 여부
    val isVideoPrepared = remember(mExoPlayer){
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        if (isVideoPrepared.value){
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
    }

    DisposableEffect(mExoPlayer){
        val listener = object : Player.Listener{
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                if (isPlaying){
                    isVideoPrepared.value = true
                    mExoPlayer.removeListener(this)
                }
            }
        }

        mExoPlayer.addListener(listener)
        onDispose {
            mExoPlayer.removeListener(listener)
        }
    }

    LaunchedEffect(mExoPlayer){
        coroutineScope.launch(Dispatchers.Main){
            itemViewModel.fetchFavEmployee()
            itemViewModel.fetchAllCategory()

            while(!isVideoPrepared.value){
                delay(100)
            }

            delay(2000)

            navController.navigate(Screens.HomeScreen.name)
        }
    }
}