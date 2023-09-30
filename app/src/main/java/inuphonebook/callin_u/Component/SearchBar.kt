package inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import inuphonebook.R
import inuphonebook.ui.theme.BlueGray
import inuphonebook.ui.theme.Gray3
import inuphonebook.ui.theme.INUPhoneBookTheme
import inuphonebook.ui.theme.White

@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    value : String = "",
    onValueChange : (String) -> Unit,
    fontSize : TextUnit = 20.sp,
    textColor : Color = Color.Black,
    placeHolder : String,
    trailingIcon : Int,
    onTrailingClick : () -> Unit,
    onKeyboardDone : () -> Unit,
    shape : Shape = RoundedCornerShape(size = 25.dp)
){
    EditText(
        modifier = modifier
            .background(
                color = if (isSystemInDarkTheme()) Gray3 else BlueGray,
                shape = shape
            ),
        fontSize = fontSize,
        textColor = textColor,
        value = value,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        placeholder = placeHolder,
        onKeyboardDone = onKeyboardDone,
        onTrailingClick = onTrailingClick,
        shape = shape
    )
}

@Preview
@Composable
fun TestSearchBar(){
    INUPhoneBookTheme {

        //검색 내용
        var searchContent by remember{ mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = White),
            contentAlignment = Alignment.Center
        ){
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                value = searchContent,
                onValueChange = {content ->
                    searchContent = content
                },
                trailingIcon = R.drawable.search,
                onTrailingClick = { },
                placeHolder = "상세 정보를 입력하세요",
                onKeyboardDone = {  },
            )
        }
    }
}