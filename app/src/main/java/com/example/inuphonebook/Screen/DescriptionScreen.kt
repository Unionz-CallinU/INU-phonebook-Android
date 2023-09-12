package com.example.inuphonebook.Screen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.inuphonebook.Component.AlertDialog
import com.example.inuphonebook.Component.CategorySpinner
import com.example.inuphonebook.Component.EmployeePage
import com.example.inuphonebook.Component.SelectCategoryDialog
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.DarkModeBackground
import com.example.inuphonebook.ui.theme.White
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DescriptionScreen(
    navController : NavController,
    itemViewModel: ItemViewModel,
    _id : String
){
    val TAG = "DescriptionScreen"

    val configuration = LocalConfiguration.current
    
    //화면 넓이, 높이
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    //전체 categoryList
    val categories = itemViewModel.categoryList.observeAsState()
    
    //현재 employee
    val id = _id.toLong() //현재는 String으로 받기에 Long형으로 받는다면 바로 받아오면 될 듯
    var employee = itemViewModel.getEmployeeById(id) ?: throw NullPointerException("Error : Employee is NULL on ${TAG}")

    //현재 category
    var selectedCategory by remember{mutableStateOf(employee.category ?: "기본")}

    //dialog들의 상태
    var showDialog by remember{mutableStateOf(false)}
    var showCheckDialog by remember{mutableStateOf(false)}

    //배경 색
    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

//    BackHandler {
//        itemViewModel.updateEmployeeCategory(employee, selectedCategory)
//        navController.navigateUp()
//    }

    if (showDialog){
        //즐겨찾기가 안되어 있다면 >> 추가
        if (!employee.isFavorite){
            //즐겨찾기 추가를 확인하는 Dialog
            SelectCategoryDialog(
                modifier = Modifier
                    .width((screenWidth / 10) * 8)
                    .height((screenHeight / 10) * 2),
                onDismissRequest = {
                    showDialog = false
                },
                categoryList = categories.value ?: throw NullPointerException("Error : categoryList is NULL on ${TAG}"),
                title = "즐겨찾기",
                message = "즐겨찾기목록에 추가하시겠습니까?",
                cancelMsg = "취소",
                okMsg = "확인",
                onOkClick = {
                    itemViewModel.insertEmployee(employee, selectedCategory)
                    employee.isFavorite = true
                    showCheckDialog = true
                    showDialog = false
                },
                width = screenWidth / 10 * 8,
            )
        } 
        //즐겨찾기가 되어있다면 >> 삭제
        else {
            AlertDialog(
                modifier = Modifier
                    .width(screenWidth / 10 * 8)
                    .height(screenHeight / 5),
                onDismissRequest = {
                    showDialog = false
                },
                highlightText = employee.name,
                baseText = "님이\n즐겨찾기목록에서 삭제 되었습니다.",
                okMsg = "확인",
                onOkClick = {
                    itemViewModel.deleteEmployee(employee.id)
                    employee.isFavorite = false
                    showDialog = false
                }
            )
        }
    }

    if (showCheckDialog){
        AlertDialog(
            modifier = Modifier
                .width(screenWidth / 10 * 8)
                .height(screenHeight / 5),
            onDismissRequest = {
                showCheckDialog = false
            },
            highlightText = employee.name,
            baseText = "님이\n즐겨찾기에 추가 되었습니다",
            okMsg = "확인",
            onOkClick = {
                showCheckDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.btn_back,
            homeClick = {
                navController.navigateUp()
            },
            favoriteIcon = if(employee.isFavorite) R.drawable.btn_minus else R.drawable.btn_plus,
            favoriteClick = {
                showDialog = true
            },
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            if (employee.isFavorite){
                CategorySpinner(
                    modifier = Modifier.fillMaxWidth(),
                    categoryList = categories.value ?: throw NullPointerException("Error : CategoryList is NULL on ${TAG}"),
                    changeItem = {
                        selectedCategory = it
                    },
                    selectedCategory = selectedCategory
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center
            ){
                Spacer(Modifier.height(30.dp))
                EmployeePage(
                    employee = employee,
                    context = LocalContext.current
                )
            }
        }

        LaunchedEffect(selectedCategory){
            employee = itemViewModel.updateEmployeeCategory(employee,selectedCategory).await()
            itemViewModel.fetchFavEmployee()
        }
    }
}