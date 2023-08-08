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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.inuphonebook.Component.CustomAddCategoryDialog
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun EditCategoryScreen(
    itemViewModel : ItemViewModel,
    navController : NavController
){
    val TAG = "EditCategoryScreen"

    val categoryList = itemViewModel.categoryList.observeAsState()

    var showDialog by remember{mutableStateOf(false)} //dialog의 상태 조절

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var newCategory by remember{mutableStateOf("")} //category 입력

    val checkList = mutableListOf<FavCategory>()

    //차선책 필요 (현재 삭제했는지 여부 확인해서 삭제헀으면 모든 상태를 false로 돌리는 중)
    var isDelete by remember{mutableStateOf(false)}

    if (showDialog){
        CustomAddCategoryDialog(
            modifier = Modifier
                .width(screenWidth / 10 * 8)
                .height(screenHeight / 5),
            onDismissRequest = {
                showDialog = !showDialog
            },
            title = "카테고리 이름을 정해주세요",
            okMsg = "추가",
            onAddClick = {
                val categoryItem = FavCategory(
                    category = newCategory
                )
                itemViewModel.insertCategory(categoryItem)
            },
            value = newCategory,
            onChangeValue = {
                newCategory = it
            }
        )
    }
    if (categoryList.value != null){
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            TopBar(
                homeIcon = R.drawable.back_btn,
                homeClick = {
                    navController.navigateUp()
                },
                homeIconSize = 40.dp,
                favoriteIcon = null,
                title = "즐겨찾기 편집"
            )
            Spacer(Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ){
                IconButton(
                    onClick = {
                        showDialog = !showDialog
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.plus_btn),
                        contentDescription = "Add Category"
                    )
                }
                Spacer(Modifier.width(5.dp))
                IconButton(
                    onClick = {
                        deleteCategory(itemViewModel, checkList)
                        isDelete = true
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus_btn),
                        contentDescription = "Delete Category"
                    )
                }
            }
            Spacer(Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(space = 5.dp)
            ){
                itemViewModel.fetchAllCategory()
                Log.d(TAG,"현재 categoryList : ${categoryList}")

                items(categoryList.value!!) {category ->
                    var isSelected by remember{mutableStateOf(false)}

                    if (isDelete){
                        isSelected = false
                        if (category == categoryList.value!!.last()){
                            isDelete = false
                        }
                    }

                    Row(
                        modifier = Modifier
                            .height(35.dp)
                            .fillMaxWidth()
                            .background(color = FillNotFavoriteColor),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                isSelected = !isSelected
                                if (isSelected){
                                    checkList.add(category)
                                } else {
                                    checkList.remove(category)
                                }
                            })
                        {
                            Icon(
                                modifier = Modifier.clip(shape = CircleShape),
                                painter = if (isSelected) painterResource(R.drawable.check_btn) else painterResource(R.drawable.non_check_btn),
                                contentDescription = "Check Box",
                                tint = Color.Unspecified
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = category.category,
                            fontSize = 20.sp,
                            color = Color.Blue,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            letterSpacing = 1.sp
                        )
                    }
                }
            }
        }
    } else {
        throw NullPointerException("Error : categoryList.value is NULL")
    }
}

private fun deleteCategory(itemViewModel : ItemViewModel, list : MutableList<FavCategory>){
    list.forEach{
        itemViewModel.deleteCategory(it.id)
    }
    list.clear()
}

@Preview
@Composable
fun TestEditCategoryScreen(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        ){
            EditCategoryScreen(
                itemViewModel = ItemViewModel(LocalContext.current),
                navController = rememberNavController()
            )
        }
    }
}