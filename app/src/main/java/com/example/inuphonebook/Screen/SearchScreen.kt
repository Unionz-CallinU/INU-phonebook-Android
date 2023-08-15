package com.example.inuphonebook.Screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.CustomAlertDialog
import com.example.inuphonebook.Component.CustomCheckDialog
import com.example.inuphonebook.Component.ListItem
import com.example.inuphonebook.Component.Logo
import com.example.inuphonebook.Component.SearchBar
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun SearchScreen(
    itemViewModel : ItemViewModel,
    navController : NavController,
    _searchContent : String,
){
    val TAG = "SearchScreen"
    val context = LocalContext.current

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    //검색 내용 저장
    var searchContent by remember{mutableStateOf(_searchContent)}

    val employeeDatas = itemViewModel.employeeDatas.observeAsState()

    val coroutineScope = rememberCoroutineScope()

    var type by remember{mutableStateOf("")}
    var showDialog by remember{mutableStateOf(false)}

    if (showDialog){
        when (type) {
            "delete" -> {
                CustomCheckDialog(
                    modifier = Modifier
                        .width(screenWidth / 10 * 8)
                        .height(screenHeight / 4),
                    onDismissRequest = {
                        showDialog = false
                    },
                    newCategory = "즐겨찾기 삭제 되었습니다.",
                    msg = "",
                    okMsg = "확인"
                )
            }
            "insert" -> {
                CustomCheckDialog(
                    modifier = Modifier
                        .width(screenWidth / 10 * 8)
                        .height(screenHeight / 4),
                    onDismissRequest = {
                        showDialog = false
                    },
                    newCategory = "즐겨찾기 추가 되었습니다.",
                    msg = "",
                    okMsg = "확인"
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.home,
            homeClick = {
                navController.navigate(Screens.HomeScreen.name)
            },
            homeIconSize = 40.dp,
            favoriteIcon = R.drawable.favorite,
            favoriteClick = {
                navController.navigate(Screens.FavoriteScreen.name)
            }
        )

        Spacer(Modifier.height(25.dp))

        Text(
            text = searchContent,
            fontSize = 22.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(17.dp))

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 37.5.dp),
            value = searchContent,
            onValueChange = {content ->
                searchContent = content
            },
            onKeyboardDone = {
                if (searchContent == ""){
                    showToast(
                        context = context,
                        msg = "검색 내용을 입력해주세요"
                    )
                } else {
                    coroutineScope.launch(Dispatchers.IO){
                        /** 여기서 async를 사용해서 await()하다보니 속도가 좀 걸리는 듯? >> 구조를 Flow 구조로 바꿔보기 */
                        val resultMsg = itemViewModel.search(searchContent).await()
                        withContext(Dispatchers.Main){
                            /** contextSwitching을 사용하지 않고 showToast 대신 AlertDialog를 사용해도 될 것 같음 */
                            if (resultMsg != "Success" && resultMsg != "Result is NULL"){
                                showToast(context, resultMsg)
                            }
                        }
                    }
                }
            },
            placeHolder = "상세 정보를 입력하세요",
            trailingIcon = R.drawable.search_icon,
            onTrailingClick = {
                if (searchContent == ""){
                    showToast(
                        context = context,
                        msg = "검색 내용을 입력해주세요"
                    )
                } else {
                    coroutineScope.launch(Dispatchers.IO){
                        val resultMsg = itemViewModel.search(searchContent).await()
                        withContext(Dispatchers.Main){
                            /** 여기도 마찬가지 */
                            if (resultMsg != "Success" && resultMsg != "Result is NULL"){
                                showToast(context, resultMsg)
                            }
                        }
                    }

                }
            }
        )

        Spacer(Modifier.height(17.dp))

        //if (employeeDatas의 데이터가 비어있으면 로고만 띄워놓기)
        if (employeeDatas.value?.size == 0){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.Center
            ){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Logo(
                        height = 100.dp,
                        width = 76.dp,
                        logoIcon = R.drawable.main_logo,
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    Spacer(Modifier.height(15.dp))
                    Text(
                        text = "검색 결과가 없습니다",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
        } else {
            Spacer(Modifier.width(20.dp))
            LazyColumn{
                items(employeeDatas.value!!){employee ->
                    ListItem(
                        employee = employee,
                        onClick = {
                            navController.navigate(
                                route = "${Screens.DescriptionScreen.name}/${employee.id}"
                            )
                        },
                        onFavoriteClick = {
                            if (employee.isFavorite){
                                itemViewModel.deleteEmployee(employee.id)
                                type = "delete"
                            } else {
                                itemViewModel.insertEmployee(employee,"기본")
                                type = "insert"
                            }
                            employee.isFavorite = !employee.isFavorite
                            showDialog = true
                        }
                    )
                }
            }
        }
    }
}

private fun showToast(context : Context, msg : String,){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

@Composable
@Preview
fun TestSearchScreen(){
    INUPhoneBookTheme {
        SearchScreen(
            itemViewModel = ItemViewModel(LocalContext.current),
            navController = rememberNavController(),
            _searchContent = "정보 대학"
        )
    }
}