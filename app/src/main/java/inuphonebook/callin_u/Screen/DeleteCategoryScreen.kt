package inuphonebook.callin_u.Screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import inuphonebook.Component.CheckDialog
import inuphonebook.Component.TopBar
import inuphonebook.LocalDB.FavCategory
import inuphonebook.Model.ItemViewModel
import inuphonebook.Model.Screens
import inuphonebook.R
import inuphonebook.callin_u.Component.CategoryItem
import inuphonebook.callin_u.showToast
import inuphonebook.ui.theme.Black
import inuphonebook.ui.theme.DarkModeBackground
import inuphonebook.ui.theme.Gray0
import inuphonebook.ui.theme.Gray1
import inuphonebook.ui.theme.Gray3
import inuphonebook.ui.theme.Gray5
import inuphonebook.ui.theme.White

@Composable
fun DelCategoryScreen(
    itemViewModel : ItemViewModel,
    navController : NavController
){
    val TAG = "DelCategoryScreen"

    val context = LocalContext.current

    //화면 크기 변수
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    //전체 category
    val categories = itemViewModel.categoryList.observeAsState()

    //dialog의 상태
    var showDialog by remember{ mutableStateOf(false) }

    //선택된 category 모음
    val checkList = mutableListOf<FavCategory>()

    //isSelected와 isSelectedAll 사이를 정리할 flag
    var flag by remember{mutableStateOf<Boolean?>(null)}

    //selected 리스트
    val isSelectList = remember{
        val tmp = mutableListOf<Boolean>()
        categories.value?.forEach{
            tmp.add(false)
        }
        tmp
    }

    //전체 선택 상태
    var isSelectedAll by remember{mutableStateOf(false)}

    //배경 색
    val backgroundColor = if(isSystemInDarkTheme()) DarkModeBackground else White

    //추가 확인 dialog
    if (showDialog){
        CheckDialog(
            modifier = Modifier
                .width(screenWidth / 10 * 8)
                .height(screenHeight / 4),
            onDismissRequest = {
                showDialog = false
            },
            newCategory = null,
            msg = "카테고리가 삭제 되었습니다.",
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
                homeIcon = R.drawable.btn_home,
                homeClick = {
                    navController.navigate(Screens.HomeScreen.name)
                },
                homeIconSize = 26.dp,
                favoriteIcon = R.drawable.btn_back_page,
                favoriteClick = {
                    navController.navigateUp()
                }
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
                    .height(24.dp)
                    .fillMaxWidth()
                    .padding(end = 30.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                //전체 선택 버튼 추가
                IconButton(
                    onClick = {
                        isSelectedAll = !isSelectedAll
                        flag = true
                    }
                ){
                    Icon(
                        modifier = Modifier.clip(shape = CircleShape),
                        painter = if (isSelectedAll) painterResource(R.drawable.btn_checked) else painterResource(R.drawable.btn_not_checked),
                        contentDescription = "Check Box",
                        tint = if(isSelectedAll) Color.Unspecified else if(isSystemInDarkTheme()) Gray3 else Gray0
                    )
                }
                Text(
                    text = "전체 선택",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    letterSpacing = 1.sp
                )
                Spacer(Modifier.weight(1f))

                //삭제 버튼 >> deleteCategory 함수 이용
                Button(
                    modifier = Modifier.fillMaxHeight(),
                    onClick = {
                        if (checkList.isEmpty()){
                            showToast(context, "삭제할 카테고리가 없습니다.")
                        } else {
                            deleteCategory(itemViewModel, checkList)
                            isSelectedAll = false
                            showDialog = true
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gray5),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "삭제",
                        color = Black,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        letterSpacing = 1.sp
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
                    val index = categories.value?.indexOf(category) ?: throw NullPointerException("index 값이 NULL 입니다.")
                    CategoryItem(
                        category = category,
                        itemViewModel = itemViewModel,
                        context = context,
                        onChangeSelected = {
                            isSelectList[index] = !isSelectList[index]    
                            if (isSelectList[index]) {
                                checkList.add(category)
                            } else {
                                checkList.remove(category)
                            }
                            //각 item의 isSelected가 변형되었을 경우 flag를 false로 유지
                            flag = false
                            isSelectedAll = checkList.size == (categories.value?.size?.minus(1))
                            Log.d(TAG, "isSelected : ${isSelectList[index]}, isSelectedAll : ${isSelectedAll}")
                        },
                        isSelected = isSelectList[index],
                        flag = flag
                    )
                }
            }
            //isSelectedAll 변경 시 checkList 변경
            LaunchedEffect(isSelectedAll){
                Log.d(TAG, "flag : ${flag}")
                Log.d(TAG, "isSelectedAll : ${isSelectedAll}")
                if (flag != null){
                    if (flag as Boolean){
                        if (isSelectedAll){
                            //모든 CategoryItem을 선택 상태로 설정
                            categories.value?.forEach{ favCategory ->
                                if (favCategory.category != "기본" && favCategory !in checkList){
                                    checkList.add(favCategory)
                                }
                            }
                            isSelectList.forEachIndexed{ index, value  ->
                                isSelectList[index] = true
                            }
                        } else {
                            checkList.clear()
                            isSelectList.forEachIndexed{ index, value ->
                                isSelectList[index] = false
                            }
                        }
                    }
                }
                flag = null
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