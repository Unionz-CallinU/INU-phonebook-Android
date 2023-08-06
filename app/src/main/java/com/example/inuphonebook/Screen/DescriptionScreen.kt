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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.CategorySpinner
import com.example.inuphonebook.Component.EmployeePage
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun DescriptionScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    val coroutineScope = rememberCoroutineScope()

    val categoryList = itemViewModel.categoryList.observeAsState()
    val employee = itemViewModel.selectedItem.value ?: throw NullPointerException("Error : Selected Employee is NULL")

    var selectedCategory by remember{mutableStateOf(employee.category)}

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.back_btn,
            homeClick = {
                navController.navigateUp()
            },
            homeIconSize = 40.dp,
            favoriteIcon = R.drawable.tmp_favorite,
            favoriteClick = {
                navController.navigate(Screens.FavoriteScreen.name)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            verticalArrangement = Arrangement.Center
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
            Spacer(Modifier.height(40.dp))
            EmployeePage(
                employee = employee,
                context = LocalContext.current
            )
        }
        LaunchedEffect(selectedCategory){
            coroutineScope.launch(Dispatchers.IO){
                itemViewModel.updateEmployeeCategory(employee,selectedCategory!!)
                itemViewModel.fetchFavEmployee()
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