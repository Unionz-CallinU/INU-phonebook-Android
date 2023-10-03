package inuphonebook.Screen

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import inuphonebook.Component.AlertDialog
import inuphonebook.Component.ListItem
import inuphonebook.Component.Logo
import inuphonebook.ui.theme.Black
import inuphonebook.Component.CheckDialog
import inuphonebook.Component.LoadingDialog
import inuphonebook.Component.SearchBar
import inuphonebook.Component.TopBar
import inuphonebook.LocalDB.Employee
import inuphonebook.Model.ItemViewModel
import inuphonebook.Model.Screens
import inuphonebook.R
import inuphonebook.callin_u.checkInput
import inuphonebook.ui.theme.DarkModeBackground
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
    val successSearch = "직원리스트조회 성공"

    //화면
    val context = LocalContext.current

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    //네트워크 연결 관리자
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    //와이파이 or 데이터 연결 확인
    val networkCapabilities = connectivityManager.activeNetwork?.let{
        connectivityManager.getNetworkCapabilities(it)
    }

    val isWifiConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    val isCellularConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    val isConnected = isWifiConnected || isCellularConnected

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

    //네트워크 오류 dialog 상태
    var showNetworkErrorDialog by remember{mutableStateOf(false)}

    //데이터 수신 여부
    val isLoading = itemViewModel.isLoading.value

    //검색 이벤트
    val searchEvent : () -> Unit = {
        //인터넷 연결
        if (isConnected){
            if (searchContent.isBlank()){
                showToast(
                    context = context,
                    msg = "검색 내용을 입력해주세요"
                )
            } else {
                //특수 기호가 포함되어 있지 않을 때
                if (checkInput(searchContent)){
                    coroutineScope.launch(Dispatchers.IO){
                        val resultMsg = itemViewModel.search(searchContent)
                        withContext(Dispatchers.Main){
                            if (resultMsg == successSearch || resultMsg == "Result is NULL"){
                                navController.navigate(
                                    route = "${Screens.SearchScreen.name}/${searchContent}"
                                )
                            } else {
                                showToast(context, resultMsg)
                            }
                        }
                    }
                }
                //있을 때
                else {
                    showToast(
                        context = context,
                        msg = "특수 문자로는 검색을 허용하지 않습니다."
                    )
                }
            }
        }
        //미 연결 시
        else {
            showNetworkErrorDialog = true
        }
    }

    //배경 색
    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

    BackHandler {
        navController.navigate(Screens.HomeScreen.name)
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

    //즐겨찾기 삭제 혹은 추가 dialog
    if (showDialog){
        selectedEmployee ?: throw NullPointerException("Error : Selected Employee is NULL on ${TAG}")
        val onDismissRequest = {
            showDialog = false
        }
        when (eventType) {
            "delete" -> {
                AlertDialog(
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
                AlertDialog(
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

    //수신되는 동안 Loading Dialog
    if (!isLoading){
        LoadingDialog(
            onDismissRequest = {},
            width = 100.dp,
            height = 70.dp,
        )
    }

    //검색 내용
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.btn_home,
            homeClick = {
                navController.navigate(Screens.HomeScreen.name)
            },
            favoriteIcon = R.drawable.not_favorite,
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
            onKeyboardDone = searchEvent,
            placeHolder = "상세 정보를 입력하세요",
            trailingIcon = R.drawable.search,
            onTrailingClick = searchEvent,
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