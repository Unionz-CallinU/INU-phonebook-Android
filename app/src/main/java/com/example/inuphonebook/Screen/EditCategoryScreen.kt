package com.example.inuphonebook.Screen

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

    //화면 크기 변수
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    //전체 category
    val categories = itemViewModel.categoryList.observeAsState()

    //dialog의 상태
    var showDialog by remember{mutableStateOf(false)}
    var showCheckDialog by remember{mutableStateOf(false)}

    //추가될 category
    var newCategory by remember{mutableStateOf("")}

    //선택된 category 모음
    val checkList = mutableListOf<FavCategory>()

    var eventType by remember{mutableStateOf("")}

    //삭제 여부
    var isDelete by remember{mutableStateOf(false)}

    //event type에 따른 dialog
    if (showDialog){
        when (eventType){
            "Insert" -> {
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
                throw IllegalArgumentException("Error : This type is not allowed on ${TAG}")
            }
        }
    }

    //추가 확인 dialog
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

    //categories 데이터가 있다면
    if (categories.value != null){
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
                        eventType = "Insert"
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
                            eventType = "Delete"
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
                items(categories.value!!) {category ->
                    var isSelected by remember{mutableStateOf(false)}

                    //delete되면서 그려진 check 초기화
                    if (isDelete){
                        isSelected = false
                        if (category == categories.value!!.last()){
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
    }
    //categories가 없다면
    else {
        throw NullPointerException("Error : categoryList.value is NULL on ${TAG}")
    }
}

//categories 삭제 함수
private fun deleteCategory(itemViewModel : ItemViewModel, list : MutableList<FavCategory>){
    list.forEach{
        itemViewModel.deleteCategory(it.id)
    }
    list.clear()
}