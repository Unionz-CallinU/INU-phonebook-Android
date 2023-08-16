package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.DividerLineColor
import com.example.inuphonebook.ui.theme.Gray1
import com.example.inuphonebook.ui.theme.Gray3
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import com.example.inuphonebook.ui.theme.Yellow

@Composable
fun ListItem(
    employee : Employee,
    onClick : () -> Unit,
    onFavoriteClick : () -> Unit,
){
    Column(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                onClick()
            }
            .background(color = White),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            Modifier
                .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column{
                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = employee.name,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        color = Black,
                        letterSpacing = 1.sp
                    )

                    Spacer(Modifier.width(11.dp))

                    Text(
                        text = employee.college_name,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        color = Gray3,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = employee.phoneNumber,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Black,
                    letterSpacing = 1.sp
                )

                Spacer(Modifier.weight(1f))
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = onFavoriteClick
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.filled_star),
                    contentDescription = "Is Favorite",
                    tint = if(employee.isFavorite) Yellow else Gray1
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = DividerLineColor
        )
    }
}

@Composable
@Preview
fun TestListItem(){
    INUPhoneBookTheme {
        Box(
            Modifier.fillMaxSize().background(color = White)
        ){
            val dummyItem = Employee(
                photo = "",
                name = "서호준",
                role = "연구생",
                phoneNumber = "010-6472-3783",
                isFavorite = true,
                id = 0,
                college_name = "정보통신대학",
                department_name = "컴퓨터공학부",
                email = "seohojon@naver.com",
                category = "기본"
            )
            ListItem(
                employee = dummyItem,
                onClick = {},
                onFavoriteClick = {},
            )
        }
    }
}