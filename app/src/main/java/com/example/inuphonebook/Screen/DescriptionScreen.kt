package com.example.inuphonebook.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.inuphonebook.Component.EmployeePage
import com.example.inuphonebook.Component.ProfessorPage
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.LocalDB.Professor
import com.example.inuphonebook.Model.Item
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
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopBar(
            homeIcon = R.drawable.tmp_home,
            homeClick = {
                navController.navigate(Screens.SearchScreen.name)
            },
            favoriteIcon = R.drawable.tmp_favorite,
            favoriteClick = {
                when (itemViewModel.selectedItem.value){
                    is Employee -> {
                        val employee = itemViewModel.selectedItem.value as Employee
                        itemViewModel.updateEmployee(employee)
                    }
                    is Professor -> {
                        val professor = itemViewModel.selectedItem.value as Professor
                        itemViewModel.updateProfessor(professor)
                    }
                }
            }
        )
        Column{
            Spacer(Modifier.height(50.dp))

            //선택된 item
            when (itemViewModel.selectedItem.value){
                is Employee -> {
                    val employee = itemViewModel.selectedItem.value as Employee
                    EmployeePage(employee)
                }
                is Professor -> {
                    val professor = itemViewModel.selectedItem.value as Professor
                    ProfessorPage(professor)
                }
                else -> throw IllegalArgumentException("Type is Error")
            }
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
            DescriptionScreen(
                navController = rememberNavController(),
                itemViewModel = ItemViewModel(LocalContext.current)
            )
        }
    }
}