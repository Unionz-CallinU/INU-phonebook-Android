package com.example.inuphonebook.Screen

import android.content.Context
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
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
import com.example.inuphonebook.Component.CategorySpinner
import com.example.inuphonebook.Component.CustomAddCategoryDialog
import com.example.inuphonebook.Component.CustomAlertDialog
import com.example.inuphonebook.Component.CustomSelectDialog
import com.example.inuphonebook.Component.EmployeePage
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Gray3
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DescriptionScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    val TAG = "DescriptionScreen"

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val coroutineScope = rememberCoroutineScope()

    val categoryList = itemViewModel.categoryList.observeAsState()
    val employee by remember{mutableStateOf(itemViewModel.selectedItem.value ?: throw NullPointerException("Error : Selected Employee is NULL"))}

    var selectedCategory by remember{mutableStateOf(employee.category)}

    var showDialog by remember{mutableStateOf(false)}
    var showCheckDialog by remember{mutableStateOf(false)}

    if (showDialog){
        if (!employee.isFavorite){
            CustomSelectDialog(
                modifier = Modifier
                    .width(screenWidth / 10 * 8)
                    .height(screenHeight / 5),
                onDismissRequest = {
                    showDialog = false
                },
                categoryList = categoryList.value ?: throw NullPointerException("Error : categoryList is NULL"),
                title = "즐겨찾기",
                message = "즐겨찾기목록에 추가하시겠습니까?",
                cancelMsg = "취소",
                okMsg = "확인",
                onOkClick = {
                    itemViewModel.insertEmployee(employee, selectedCategory ?: "기본")
                    employee.isFavorite = true
                    showCheckDialog = true
                    showDialog = false
                },
                width = screenWidth / 10 * 8,
            )
        } else {
            CustomAlertDialog(
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
        CustomAlertDialog(
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
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Log.d("DescriptionScreen","selectecItem : ${employee}")
        Log.d("DescriptionScreen","all items : ${itemViewModel.favEmployeeDatas.value}")
        TopBar(
            homeIcon = R.drawable.back_btn,
            homeClick = {
                navController.navigateUp()
            },
            homeIconSize = 40.dp,
            favoriteIcon = if(employee.isFavorite) R.drawable.minus_btn else R.drawable.plus_btn,
            favoriteClick = {
                showDialog = true
            }
        )
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            if (employee.isFavorite){
                CategorySpinner(
                    modifier = Modifier.fillMaxWidth(), //itemViewModel에서 가져온 category
                    categoryList = categoryList.value ?: throw NullPointerException("CategoryList is NULL"),
                    changeItem = {
                        selectedCategory = it
                    },
                    selectedCategory = selectedCategory ?: "기본"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp, top = 10.dp),
                verticalArrangement = Arrangement.Center
            ){
                Spacer(Modifier.height(40.dp))
                EmployeePage(
                    employee = employee,
                    context = LocalContext.current
                )
            }

        }
        LaunchedEffect(selectedCategory){
            if (selectedCategory != null){
                coroutineScope.launch(Dispatchers.IO){
                    itemViewModel.updateEmployeeCategory(employee,selectedCategory!!)
                    itemViewModel.fetchFavEmployee()
                }
            }
        }
    }
}
private fun showToast(context : Context, msg : String,){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}
@Preview
@Composable
fun TestDescriptionScreen(){
    INUPhoneBookTheme {
        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            val employee = Employee(
                name = "서호준",
                role = "학생",
                phoneNumber = "010-6472-3783",
                isFavorite = true,
                photo = "",
                id = 0,
                department_name = "컴퓨터 공학부",
                college_name = "정보통신대학",
                email = "seohojon@naver.com",
                category = "기본"
            )
            val itemViewModel = ItemViewModel(LocalContext.current)
            itemViewModel.setSelectedItem(employee)
            DescriptionScreen(
                navController = rememberNavController(),
                itemViewModel = itemViewModel
            )
        }
    }
}