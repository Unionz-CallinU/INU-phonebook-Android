package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Blue
import com.example.inuphonebook.ui.theme.BlueGray
import com.example.inuphonebook.ui.theme.Gray2
import com.example.inuphonebook.ui.theme.Gray3
import com.example.inuphonebook.ui.theme.Gray4

@Composable
fun CategorySpinner(
    modifier : Modifier = Modifier,
    categoryList : MutableList<FavCategory>,
    changeItem : (String) -> Unit,
    selectedCategory : String,
    fontFamily : FontFamily = FontFamily(Font(R.font.pretendard_medium))
){
    //spinner의 상태
    var isOpen by remember{ mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = if (isSystemInDarkTheme()) Gray4 else BlueGray
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(40.dp)
                .clickable {
                    isOpen = !isOpen
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = selectedCategory,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Blue,
                fontFamily = fontFamily,
                letterSpacing = 1.sp
            )
            Spacer(Modifier.width(5.dp))
            IconButton(
                modifier = Modifier.size(10.dp),
                //spinner open
                onClick = {
                    isOpen = !isOpen
                }
            ) {
                Icon(
                    painter = if (isOpen) painterResource(R.drawable.btn_close_spinner) else painterResource(
                        R.drawable.btn_open_spinner
                    ),
                    contentDescription = "Spinner Button",
                    tint = Gray3
                )
            }
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = if (isSystemInDarkTheme()) Gray2 else BlueGray),
            expanded = isOpen,
            onDismissRequest = {
                isOpen = !isOpen
            }
        ) {
            categoryList.forEach { item ->
                DropdownMenuItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    onClick = {
                        changeItem(item.category)
                        isOpen = !isOpen
                    },
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = item.category,
                            textAlign = TextAlign.Center,
                            color = Gray3,
                            fontFamily = fontFamily,
                            letterSpacing = 1.sp,
                            fontSize = 20.sp
                        )
                    }
                )
                if (item != categoryList[categoryList.lastIndex]) {
                    Divider(
                        thickness = 0.5.dp,
                        color = Gray3
                    )
                }
            }
        }
    }
}