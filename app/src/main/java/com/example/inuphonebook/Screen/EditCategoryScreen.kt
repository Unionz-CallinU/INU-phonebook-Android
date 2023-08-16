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
import androidx.compose.foundation.layout.size
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
import com.example.inuphonebook.Component.CustomCheckDialog
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
    var showCheckDialog by remember{mutableStateOf(false)}

    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    var newCategory by remember{mutableStateOf("")} //category 입력

    val checkList = mutableListOf<FavCategory>()

    var type by remember{mutableStateOf("")}

    //차선책 필요 (현재 삭제했는지 여부 확인해서 삭제헀으면 모든 상태를 false로 돌리는 중)
    var isDelete by remember{mutableStateOf(false)}

    if (showDialog){
        when (type){
            "Add" -> {
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
                        showCheckDialog = true
                    },
                    value = newCategory,
                    onChangeValue = {
                        newCategory = it
                    }
                )
            }
            "Delete" -> {
                CustomCheckDialog(
                    modifier = Modifier
                        .width(screenWidth / 10 * 8)
                        .height(screenHeight / 4),
                    onDismissRequest = {
                        showDialog = false
                    },
                    newCategory = newCategory,
                    msg = "카테고리가 삭제 되었습니다.",
                    okMsg = "확인"
                )
            }
            else -> {
                throw IllegalArgumentException("Error : 올바르지 않은 Type")
            }
        }

    }

    if (showCheckDialog){
        CustomCheckDialog(
            modifier = Modifier
                .width(screenWidth / 10 * 8)
                .height(screenHeight / 4),
            onDismissRequest = {
                newCategory = ""
                showCheckDialog = false
            },
            newCategory = newCategory,
            msg = "카테고리가 추가 되었습니다.",
            okMsg = "확인"
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
                favoriteIcon = null,
                title = "즐겨찾기 편집"
            )
            Spacer(Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                horizontalArrangement = Arrangement.End
            ){
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        showDialog = !showDialog
                        type = "Add"
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.plus_btn),
                        contentDescription = "Add Category"
                    )
                }
                Spacer(Modifier.width(10.dp))
                IconButton(
                    modifier = Modifier.size(24.dp),
                    onClick = {
                        //선택된 항목이 없이 삭제를 눌렀을 경우 다이얼로그를 띄우지 않음
                        if (checkList.size != 0){
                            deleteCategory(itemViewModel, checkList)
                            type = "Delete"
                            showDialog = true
                            isDelete = true
                        }
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
                verticalArrangement = Arrangement.spacedBy(space = 6.dp)
            ){
                itemViewModel.fetchAllCategory()
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
                            }
                        ){
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