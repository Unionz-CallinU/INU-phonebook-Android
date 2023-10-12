package inuphonebook.callin_u.Component

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inuphonebook.LocalDB.FavCategory
import inuphonebook.Model.ItemViewModel
import inuphonebook.R
import inuphonebook.callin_u.showToast
import inuphonebook.ui.theme.Blue
import inuphonebook.ui.theme.Gray0
import inuphonebook.ui.theme.Gray1
import inuphonebook.ui.theme.Gray3
import inuphonebook.ui.theme.Gray4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun CategoryItem(
    category : FavCategory,
    itemViewModel : ItemViewModel,
    context : Context,
    onChangeSelected : () -> Unit,
    isSelected : Boolean,
    flag : Boolean?
){
    val coroutineScope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .height(35.dp)
            .fillMaxWidth()
            .background(color = if (isSystemInDarkTheme()) Gray4 else Gray1),
        verticalAlignment = Alignment.CenterVertically
    ){

        //만약 "기본"을 선택할 경우 선택 버튼이 보이지 않도록 함
        if (category.category != "기본"){
            IconButton(
                modifier = Modifier.size(35.dp),
                onClick = {
                    coroutineScope.launch(Dispatchers.IO){
                        if (itemViewModel.isEmployeeInCategory(category.category)){
                            //category 내부 데이터가 있다면 선택 불가
                            withContext(Dispatchers.Main){
                                showToast(context,"카테고리 내부 데이터를 정리해주세요.\n 데이터가 남아 있습니다.")
                            }
                        } else {
                            onChangeSelected()
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
        } else {
            Spacer(Modifier.width(35.dp))
        }
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