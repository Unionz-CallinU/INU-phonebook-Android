package com.example.inuphonebook.Screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.inuphonebook.Component.CheckDialog
import com.example.inuphonebook.Component.InputCategoryDialog
import com.example.inuphonebook.Component.TopBar
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.Model.ItemViewModel
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.Blue
import com.example.inuphonebook.ui.theme.DarkModeBackground
import com.example.inuphonebook.ui.theme.Gray0
import com.example.inuphonebook.ui.theme.Gray1
import com.example.inuphonebook.ui.theme.Gray2
import com.example.inuphonebook.ui.theme.Gray3
import com.example.inuphonebook.ui.theme.Gray4
import com.example.inuphonebook.ui.theme.White

@Composable
fun EditCategoryScreen(
    itemViewModel : ItemViewModel,
    navController : NavController
){
    val TAG = "EditCategoryScreen"

    val context = LocalContext.current

    //화면 크기 변수
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    //전체 category
    val categories = itemViewModel.categoryList.observeAsState()

    //dialog의 상태
    var showDialog by remember{mutableStateOf(false)}
    var showDelCheckDialog by remember{mutableStateOf(false)}
    var showCheckDialog by remember{mutableStateOf(false)}

    //추가될 category
    var newCategory by remember{mutableStateOf("")}

    //선택된 category 모음
    val checkList = mutableListOf<FavCategory>()

    var eventType by remember{mutableStateOf("")}

    //삭제 여부
    var isDelete by remember{mutableStateOf(false)}

    //배경 색
    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

    //event type에 따른 dialog
    if (showDialog){
        when (eventType){
            "Insert" -> {
                InputCategoryDialog(
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
                //category에 포함된 데이터들이 있음을 알리고 그래도 삭제할 것인지 확인 받는 dialog? 아니면 자동으로 기본 Category로 수정?
                CheckDialog(
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
        CheckDialog(
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
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor)
        ){
            TopBar(
                homeIcon = R.drawable.btn_back,
                homeClick = {
                    navController.navigateUp()
                },
                homeIconSize = 26.dp,
                favoriteIcon = null,
            )
            Spacer(Modifier.height(35.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "즐겨찾기 편집",
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
                        painter = painterResource(R.drawable.btn_plus),
                        contentDescription = "Add Category",
                        tint = if(isSystemInDarkTheme()) Gray2 else Black
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
                        painter = painterResource(R.drawable.btn_minus),
                        contentDescription = "Delete Category",
                        tint = if(isSystemInDarkTheme()) Gray2 else Black
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
                            .background(color = if (isSystemInDarkTheme()) Gray4 else Gray1),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        IconButton(
                            onClick = {
                                //만약 "기본"을 선택할 경우 선택을 취소하고 알림을 띄움
                                if (category.category == "기본"){
                                    message(context, "기본 카테고리는 삭제할 수 없습니다.")
                                } else {
                                    isSelected = !isSelected
                                    if (isSelected){
                                        checkList.add(category)
                                    } else {
                                        checkList.remove(category)
                                    }
                                }
                            }
                        ){
                            Icon(
                                modifier = Modifier.clip(shape = CircleShape),
                                painter = if (isSelected) painterResource(R.drawable.btn_checked) else painterResource(R.drawable.btn_not_checked),
                                contentDescription = "Check Box",
                                tint = if(isSelected) Color.Unspecified else if(isSystemInDarkTheme()) Gray3 else Gray0
                            )
                        }
                        Spacer(Modifier.width(10.dp))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = category.category,
                            fontSize = 20.sp,
                            color = Blue,
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

//toast 메세지
private fun message(context : Context, msg : String) {
    Toast.makeText(
        context,
        msg,
        Toast.LENGTH_SHORT
    ).show()
}