package com.example.inuphonebook.Screen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.DarkModeBackground
import com.example.inuphonebook.ui.theme.Gray4
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

    //화면
    val context = LocalContext.current

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    //검색 내용 저장
    var searchContent by remember{mutableStateOf(_searchContent)}

    //즐겨찾기 데이터
    val employees = itemViewModel.employees.observeAsState()

    val coroutineScope = rememberCoroutineScope()

    var eventType by remember{mutableStateOf("")}

    //dialog 상태
    var showDialog by remember{mutableStateOf(false)}

    //선택된 Employee
    var selectedEmployee by remember{mutableStateOf<Employee?>(null)}

    //배경 색
    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

    BackHandler {
        navController.navigate(Screens.HomeScreen.name)
    }
    
    //즐겨찾기 삭제 혹은 추가 dialog
    if (showDialog){
        selectedEmployee ?: throw NullPointerException("Error : Selected Employee is NULL on ${TAG}")
        val onDismissRequest = {
            showDialog = false
        }
        when (eventType) {
            "delete" -> {
                CustomAlertDialog(
                    modifier = Modifier
                        .width(screenWidth / 10 * 8)
                        .height(screenHeight / 4),
                    onDismissRequest = onDismissRequest,
                    highlightText = selectedEmployee!!.name,
                    baseText = " 님이\n즐겨찾기 목록에서 삭제 되었습니다.",
                    okMsg = "확인",
                    onOkClick = onDismissRequest
                )
            }
            "insert" -> {
                CustomAlertDialog(
                    modifier = Modifier
                        .width(screenWidth / 10 * 8)
                        .height(screenHeight / 4),
                    onDismissRequest = onDismissRequest,
                    highlightText = selectedEmployee!!.name,
                    baseText = " 님이\n즐겨찾기 목록에 추가 되었습니다.",
                    okMsg = "확인",
                    onOkClick = onDismissRequest
                )
            }
        }
    }

    //검색 내용
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.home,
            homeClick = {
                navController.navigate(Screens.HomeScreen.name)
            },
            favoriteIcon = R.drawable.favorite,
            favoriteClick = {
                navController.navigate(Screens.FavoriteScreen.name)
            },
        )

        Spacer(Modifier.height(25.dp))

        Text(
            text = searchContent,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            color = if(isSystemInDarkTheme()) White else Black,
            letterSpacing = 2.sp
        )

        Spacer(Modifier.height(25.dp))

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
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
                        val resultMsg = itemViewModel.search(searchContent).await()
                        withContext(Dispatchers.Main){
                            if (resultMsg != "Success" && resultMsg != "Result is NULL"){
                                showToast(context, resultMsg)
                            } else {
                                navController.navigate(
                                    route = "${Screens.SearchScreen.name}/${searchContent}"
                                )
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
                            if (resultMsg != "Success" && resultMsg != "Result is NULL"){
                                showToast(context, resultMsg)
                            } else {
                                navController.navigate(
                                    route = "${Screens.SearchScreen.name}/${searchContent}"
                                )
                            }
                        }
                    }
                }
            }
        )

        Spacer(Modifier.height(80.dp))

        //if (employeeDatas의 데이터가 비어있으면 로고만 띄워놓기)
        if (employees.value?.size == 0){
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
            LazyColumn{
                items(employees.value!!){employee ->
                    ListItem(
                        employee = employee,
                        onClick = {
                            navController.navigate(
                                route = "${Screens.DescriptionScreen.name}/${employee.id}"
                            )
                        },
                        onFavoriteClick = {
                            eventType = if (employee.isFavorite){
                                itemViewModel.deleteEmployee(employee.id)
                                "delete"
                            } else {
                                itemViewModel.insertEmployee(employee,"기본")
                                "insert"
                            }
                            employee.isFavorite = !employee.isFavorite
                            selectedEmployee = employee
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