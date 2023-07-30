package com.example.inuphonebook.Screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.CustomSpinner
import com.example.inuphonebook.Component.EmployeePage
import com.example.inuphonebook.Component.ProfessorPage
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.Model.Screens
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun DescriptionScreen(
    navController : NavController,
    itemViewModel: ItemViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.tmp_home,
            homeClick = {
                navController.navigate(Screens.SearchScreen.name)
            },
            homeIconSize = 40.dp,
            favoriteIcon = R.drawable.tmp_favorite,
            favoriteClick = {
//                val employee = itemViewModel.selectedItem.value as Employee
//                itemViewModel.updateEmployee(employee)
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp),
            verticalArrangement = Arrangement.Center
        ){
            val employee = itemViewModel.selectedItem.value ?: throw NullPointerException("Error : Selected Employee is NULL")
            if (employee.isFavorite){
                CustomSpinner(
                    modifier = Modifier.fillMaxWidth(), //itemViewModel에서 가져온 category
                )
            }
            Spacer(Modifier.height(40.dp))
            EmployeePage(employee)
        }
    }
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
                position = "학부 연구생",
                phoneNumber = "010-6472-3783",
                isFavorite = true,
                photo = R.drawable.splash_logo,
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