package inuphonebook.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import inuphonebook.Component.AlertDialog
import inuphonebook.Component.ListItem
import inuphonebook.Component.Logo
import inuphonebook.R
import inuphonebook.ui.theme.Black
import inuphonebook.ui.theme.Blue
import inuphonebook.ui.theme.White
import inuphonebook.Component.TopBar
import inuphonebook.LocalDB.Employee
import inuphonebook.Model.ItemViewModel
import inuphonebook.Model.Screens
import inuphonebook.ui.theme.DarkModeBackground
import inuphonebook.ui.theme.Gray1
import inuphonebook.ui.theme.Gray2
import inuphonebook.ui.theme.Gray4

@Composable
fun FavoriteScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    val TAG = "FavoriteScreen"

    //local 화면 크기
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    //localDB내 favorite 리스트
    val favoriteEmployees = itemViewModel.favEmployees.observeAsState()
    val categories = itemViewModel.categoryList.observeAsState()

    //dialog의 상태
    var showCheckDialog by remember{mutableStateOf(false)}

    var eventType by remember{mutableStateOf("")}

    //선택된 item
    var selectedItem by remember{mutableStateOf<Employee?>(null)}

    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

    //scroll 상태
    val scrollState = rememberScrollState()

    if (showCheckDialog){
        when (eventType) {
            "Delete" -> AlertDialog(
                modifier = Modifier
                    .width(screenWidth / 10 * 8)
                    .height(screenHeight / 5),
                highlightText = selectedItem!!.name,
                baseText = "님이\n즐겨찾기목록에서 삭제 되었습니다.",
                okMsg = "확인",
                onDismissRequest = {
                    showCheckDialog = false
                },
                onOkClick = {
                    showCheckDialog = false
                }
            )
            else -> {
                throw IllegalArgumentException("Error : Type is not allowed on ${TAG}")
            }
        }
        itemViewModel.fetchFavEmployee()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor)
    ){
        TopBar(
            title = "",
            homeIcon = R.drawable.btn_back,
            homeIconSize = 26.dp,
            favoriteIcon = null,
            homeClick = {
                navController.navigateUp()
            },
        )
        Spacer(Modifier.height(35.dp))

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "즐겨찾기 목록",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
            color = if(isSystemInDarkTheme()) Gray1 else Black,
            letterSpacing = 2.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(45.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                modifier = Modifier.size(25.dp),
                onClick = {
                    navController.navigate(Screens.EditCategoryScreen.name)
                }
            ){
                Icon(
                    painter = painterResource(R.drawable.btn_edit_favorite),
                    contentDescription = "Favorite Edit",
                    tint = if(isSystemInDarkTheme()) Gray2 else Black
                )
            }
            Spacer(Modifier.width(20.dp))
        }
        
        //favorite 아이템이 없다면
        if (favoriteEmployees.value!!.isEmpty()){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp),
                contentAlignment = Alignment.Center
            ){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Logo(
                        height = 100.dp,
                        width = 76.dp,
                        logoIcon = R.drawable.main_logo_monochrome,
                        colorFilter = null
                    )
                    Spacer(Modifier.height(52.dp))
                    Text(
                        text = "즐겨찾기 목록에 추가된\n연락처가 없습니다",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
        } 
        //favorite 아이템이 있다면
        else {
            Spacer(Modifier.height(14.dp))
            Column(
                modifier = Modifier.fillMaxWidth()
                    .verticalScroll(state = scrollState),
            ){
                categories.value!!.forEach{ category ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(35.dp)
                                .background(color = if (isSystemInDarkTheme()) Gray4 else Gray1),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Spacer(Modifier.width(20.dp))
                            Text(
                                text = category.category,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                color = Blue,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(5.dp))

                    Spacer(Modifier.height(5.dp))

                    Column{
                        favoriteEmployees.value!!.forEach{employee ->
                            if (employee.category == category.category){
                                ListItem(
                                    employee = employee,
                                    onClick = {
                                        navController.navigate(
                                            route = "${Screens.DescriptionScreen.name}/${employee.id}"
                                        )
                                    },
                                    onFavoriteClick = {
                                        selectedItem = employee
                                        itemViewModel.deleteEmployee(employee.id)
                                        eventType = "Delete"
                                        itemViewModel.updateFavorite(employee.id)

                                        showCheckDialog = true
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}