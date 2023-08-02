package com.example.inuphonebook.Screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
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
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.RetrofitDto.EmployeeReqDto
import com.example.inuphonebook.Model.RetrofitDto.EmployeeRespBody
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.Retrofit.RetrofitClient
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun HomeScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    val connectivityManager = LocalContext.current.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //와이파이 연결 확인
    val networkCapabilities = connectivityManager.activeNetwork?.let{
        connectivityManager.getNetworkCapabilities(it)
    }

    val isWifeConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

    //Wifi에 연결이 되어있을 시
    if (isWifeConnected){
        //검색 내용
        var searchContent by remember{mutableStateOf("")}
        val coroutineScope = rememberCoroutineScope()

        val context = LocalContext.current

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            TopBar(
                homeIcon = R.drawable.tmp_home,
                homeClick = {
                },
                homeIconSize = 40.dp,
                favoriteIcon = R.drawable.tmp_favorite,
                favoriteClick = {
                    navController.navigate(Screens.FavoriteScreen.name)
                }
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Logo(
                    size = 100.dp,
                    logoIcon = R.drawable.main_logo
                )
                Spacer(Modifier.height(55.dp))
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 37.5.dp),
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
                            navController.navigate(
                                route = "${Screens.SearchScreen.name}/$searchContent",
                            )
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
                            navController.navigate(
                                route = "${Screens.SearchScreen.name}/$searchContent"
                            )
                        }
                    }
                )
            }
        }
    }
    //Wifi 미 연결 시
    else {
        Box(modifier = Modifier.fillMaxSize()){
            SplashScreen()
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