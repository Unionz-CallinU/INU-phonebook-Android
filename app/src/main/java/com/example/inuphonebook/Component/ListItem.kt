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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.DarkModeBackground
import com.example.inuphonebook.ui.theme.DividerLineColor
import com.example.inuphonebook.ui.theme.Gray1
import com.example.inuphonebook.ui.theme.Gray2
import com.example.inuphonebook.ui.theme.Gray3
import com.example.inuphonebook.ui.theme.Yellow

@Composable
fun ListItem(
    employee : Employee,
    onClick : () -> Unit,
    onFavoriteClick : () -> Unit,
){

    val textColor = if(isSystemInDarkTheme()) White else Black
    val subTextColor = if(isSystemInDarkTheme()) Gray2 else Gray3

    Column(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                onClick()
            }
            .background(
                color = if(isSystemInDarkTheme()) DarkModeBackground else White,
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .padding(start = 20.dp, end = 20.dp, top = 8.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = employee.name,
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        color = textColor,
                        letterSpacing = 1.sp
                    )

                    Spacer(Modifier.width(11.dp))

                    Text(
                        text = employee.college_name,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        color = subTextColor,
                        letterSpacing = 0.5.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                Text(
                    text = employee.phoneNumber,
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = textColor,
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
                    painter = painterResource(R.drawable.favorite),
                    contentDescription = "Is Favorite",
                    tint = if (employee.isFavorite) Yellow else if (isSystemInDarkTheme()) Gray2 else Gray1
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = if (isSystemInDarkTheme()) Gray3 else DividerLineColor
        )
    }
}