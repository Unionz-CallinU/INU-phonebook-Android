package com.example.inuphonebook.Screen

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.inuphonebook.Component.CheckDialog
import com.example.inuphonebook.Component.Logo
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.DarkModeBackground
import com.example.inuphonebook.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    val TAG = "HomeScreen"

    //화면
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val coroutineScope = rememberCoroutineScope()

    //backPressed 상태
    var backPressed by remember{mutableStateOf(false)}

    //네트워크 연결 관리자
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //와이파이 or 데이터 연결 확인
    val networkCapabilities = connectivityManager.activeNetwork?.let{
        connectivityManager.getNetworkCapabilities(it)
    }

    val isWifiConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    val isCellularConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    val isConnected = isWifiConnected || isCellularConnected

    //네트워크 오류 dialog 상태
    var showNetworkErrorDialog by remember{mutableStateOf(false)}

    //검색 내용
    var searchContent by remember{mutableStateOf("")}

    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

    //검색 event
    val searchEvent : () -> Unit = {
        //인터넷 연결
        if (isConnected){
            if (searchContent == ""){
                showToast(
                    context = context,
                    msg = "검색 내용을 입력해주세요"
                )
            } else {
                coroutineScope.launch(Dispatchers.IO){
                    val resultMsg = itemViewModel.search(searchContent).await()
                    withContext(Dispatchers.Main){
                        if (resultMsg == "Success" || resultMsg == "Result is NULL"){
                            navController.navigate(
                                route = "${Screens.SearchScreen.name}/${searchContent}"
                            )
                        } else {
                            showToast(context, resultMsg)
                        }
                    }
                }
            }
        }
        //미 연결 시
        else {
            showNetworkErrorDialog = true
        }
    }
    
    //뒤로가기 앱 종료
    BackHandler {
        //뒤로가기 두 번에 종료
        if (backPressed){
            (context as? Activity)?.finish()
        } else {
            backPressed = true
            showToast(context,"한 번 더 누르면 앱이 종료됩니다.")

            coroutineScope.launch(Dispatchers.Main){
                delay(2000)
                backPressed = false
            }
        }
    }

    //네트워크 오류 dialog + Home 화면으로 돌아감
    if (showNetworkErrorDialog) {
        val onDismissRequest = {
            showNetworkErrorDialog = false
            navController.navigate(Screens.HomeScreen.name)
        }
        CheckDialog(
            modifier = Modifier
                .width(screenWidth / 10 * 8)
                .height(screenHeight / 4),
            onDismissRequest = onDismissRequest,
            newCategory = "경고!",
            msg = "네트워크 연결이 불안정합니다.\n확인하고 다시 실행시켜 주세요",
            okMsg = "확인",
        )
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TopBar(
            homeIcon = R.drawable.btn_home,
            homeClick = {
            },
            favoriteIcon = R.drawable.not_favorite,
            favoriteClick = {
                navController.navigate(Screens.FavoriteScreen.name)
            },
        )
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Spacer(Modifier.height(145.dp))

            Logo(
                height = 100.dp,
                width = 76.dp,
                logoIcon = R.drawable.main_logo
            )

            Spacer(Modifier.height(45.dp))

            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                value = searchContent,
                onValueChange = {content ->
                    searchContent = content
                },
                trailingIcon = R.drawable.search,
                onTrailingClick = searchEvent,
                placeHolder = "상세 정보를 입력하세요",
                onKeyboardDone = searchEvent,
            )
        }
    }
}

private fun showToast(context : Context, msg : String,){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}