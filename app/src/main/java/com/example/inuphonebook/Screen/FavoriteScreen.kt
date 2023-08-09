package com.example.inuphonebook.Screen

import android.util.Log
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.CustomAlertDialog
import com.example.inuphonebook.Component.CustomCheckDialog
import com.example.inuphonebook.Component.ListItem
import com.example.inuphonebook.Component.Logo
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Blue
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun FavoriteScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    val TAG = "FavoriteScreen"

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    //localDB내 favorite 리스트
    val favoriteEmployees = itemViewModel.favEmployeeDatas.observeAsState()
    val categoryList = itemViewModel.categoryList.observeAsState()

    var showCheckDialog by remember{mutableStateOf(false)}

    var selectedItem by remember{mutableStateOf<Employee?>(null)}

    if (showCheckDialog){
        CustomAlertDialog(
            modifier = Modifier
                .width(screenWidth / 10 * 8)
                .height(screenHeight / 5),
            highlightText = "${selectedItem!!.name}",
            baseText = "님이\n즐겨찾기목록에서 삭제 되었습니다.",
            okMsg = "확인",
            onDismissRequest = {
                showCheckDialog = false
                itemViewModel.fetchFavEmployee()
            },
            onOkClick = {
                showCheckDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ){
        TopBar(
            title = "즐겨찾기 목록",
            homeIcon = R.drawable.back_btn,
            homeIconSize = 40.dp,
            favoriteIcon = null,
            homeClick = {
                navController.navigateUp()
            }
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(
                onClick = {
                    navController.navigate(Screens.EditCategoryScreen.name)
                }
            ){
                Icon(
                    painter = painterResource(R.drawable.favorite_edit_btn),
                    contentDescription = "Favorite Edit"
                )
            }
            Spacer(Modifier.width(15.dp))
        }
        //if (favorite List가 존재하지 않는다면)
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
                        logoIcon = R.drawable.main_logo,
                        colorFilter = ColorFilter.tint(Color.LightGray)
                    )
                    Spacer(Modifier.height(55.dp))
                    Text(
                        text = "즐겨찾기 목록에 추가된 연락처가 없습니다",
                        fontSize = 14.sp,
                        color = Color.LightGray
                    )
                }
            }
        } else {
            Spacer(Modifier.height(17.dp))
            //if(학과 사무실 정보의 list.size가 0이 아니라면)
            categoryList.value?.forEach{category ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(35.dp)
                        .background(color = FillNotFavoriteColor),
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
                Spacer(Modifier.height(5.dp))
                LazyColumn{
                    items(favoriteEmployees.value!!){employee ->
                        if (employee.category == category.category){
                            ListItem(
                                employee = employee,
                                onClick = {
                                    itemViewModel.setSelectedItem(employee)
                                    navController.navigate(Screens.DescriptionScreen.name)
                                },
                                onFavoriteClick = {
                                    selectedItem = employee
                                    if (employee.isFavorite){
                                        itemViewModel.deleteEmployee(employee.id)
                                    }
                                    itemViewModel.fetchFavEmployee()
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

@Preview
@Composable
fun TestFavoriteScreen(){
    INUPhoneBookTheme {
        Box(
            Modifier.fillMaxSize()
        ){
            FavoriteScreen(
                navController = rememberNavController(),
                itemViewModel = ItemViewModel(LocalContext.current)
            )
        }
    }
}