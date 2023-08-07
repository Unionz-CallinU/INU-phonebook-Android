package com.example.inuphonebook.Screen

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.Logo
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    val context = LocalContext.current
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val coroutineScope = rememberCoroutineScope()

    var backPressed by remember{mutableStateOf(false)}

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

    //와이파이 연결 확인
    val networkCapabilities = connectivityManager.activeNetwork?.let{
        connectivityManager.getNetworkCapabilities(it)
    }

    val isWifiConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    val isCellularConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    val isConnected = isWifiConnected || isCellularConnected

    //Wifi에 연결이 되어있을 시
    if (isConnected){
        //검색 내용
        var searchContent by remember{mutableStateOf("")}

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            TopBar(
                homeIcon = R.drawable.home,
                homeClick = {
                },
                homeIconSize = 24.dp,
                favoriteIcon = R.drawable.favorite,
                favoriteClick = {
                    navController.navigate(Screens.FavoriteScreen.name)
                }
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

                Spacer(Modifier.height(35.dp))

                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    value = searchContent,
                    onValueChange = {content ->
                        searchContent = content
                    },
                    trailingIcon = R.drawable.search_icon,
                    onTrailingClick = {
                        if (searchContent == ""){
                            showToast(
                                context = context,
                                msg = "검색 내용을 입력해주세요"
                            )
                        } else {
//                            val resultMsg = itemViewModel.search(searchContent)
//                            if (resultMsg == "Success" || resultMsg == "Result is NULL"){
                                navController.navigate(
                                    route = "${Screens.SearchScreen.name}/$searchContent"
                                )
//                            } else {
//                                showToast(context, resultMsg)
//                            }
                        }
                    },
                    placeHolder = "상세 정보를 입력하세요",
                    onKeyboardDone = {
                        if (searchContent == ""){
                            showToast(
                                context = context,
                                msg = "검색 내용을 입력해주세요"
                            )
                        } else {
//                            val resultMsg = itemViewModel.search(searchContent)
//                            if (resultMsg == "Success" || resultMsg == "Result is NULL"){
                                navController.navigate(
                                    route = "${Screens.SearchScreen.name}/$searchContent"
                                )
//                            } else {
//                                showToast(context, resultMsg)
//                            }
                        }
                    }
                )
            }
        }
    }
//    Wifi 미 연결 시
    else {
        Box(modifier = Modifier.fillMaxSize()){
            SplashScreen(
                itemViewModel = itemViewModel,
                navController = navController
            )
        }
    }
}

private fun showToast(context : Context, msg : String,){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun TestHomeScreen(){
    INUPhoneBookTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            HomeScreen(
                navController = rememberNavController(),
                itemViewModel = ItemViewModel(LocalContext.current)
            )
        }
    }
}