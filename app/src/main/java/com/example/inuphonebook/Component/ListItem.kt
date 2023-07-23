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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.util.TableInfo
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.LocalDB.Professor
import com.example.inuphonebook.Model.Item
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.DividerLineColor
import com.example.inuphonebook.ui.theme.FIllFavoriteColor
import com.example.inuphonebook.ui.theme.FillNotFavoriteColor
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme

@Composable
fun ListItem(
    item : Any,
    onClick : () -> Unit,
    onFavoriteClick : () -> Unit,
){
    val photo = when(item) {
        is Employee -> (item as Employee).let{item.photo}
        is Professor -> (item as Professor).let{item.photo}
        else -> throw IllegalArgumentException("Error : 규정되지 않은 타입")
    }
    val name = when(item){
        is Employee -> (item as Employee).let{item.name}
        is Professor -> (item as Professor).let{item.name}
        else -> throw IllegalArgumentException("Error : 규정되지 않은 타입")
    }
    val phone = when(item){
        is Employee -> (item as Employee).let{item.phone}
        is Professor -> (item as Professor).let{item.phone}
        else -> throw IllegalArgumentException("Error : 규정되지 않은 타입")
    }
    val isFavorite = when(item){
        is Employee -> (item as Employee).let{item.isFavorite}
        is Professor -> (item as Professor).let{item.isFavorite}
        else -> throw IllegalArgumentException("Error : 규정되지 않은 타입")
    }
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
                .padding(start = 30.dp, end = 30.dp, top = 8.dp)
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(photo),
                contentDescription = "List Item"
            )

            Spacer(Modifier.width(50.dp))

            Column{
                Spacer(Modifier.weight(1f))

                Text(
                    text = name,
                    fontSize = 15.sp
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = phone,
                    fontSize = 13.sp
                )

                Spacer(Modifier.weight(1f))
            }

            Spacer(Modifier.weight(1f))

            IconButton(
                onClick = onFavoriteClick
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    painter = painterResource(R.drawable.tmp_favorite_not),
                    contentDescription = "Is Favorite",
                    tint = if(isFavorite) FIllFavoriteColor else FillNotFavoriteColor
                )
            }
        }
        Spacer(Modifier.height(8.dp))

        Divider(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp),
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
            val dummyItem = Item(
                image = R.drawable.ic_launcher_foreground,
                name = "서호준",
                department = "정보통신대학",
                phone = "010-6472-3783",
                favorite = true
            )
            ListItem(
                item = dummyItem,
                onClick = {},
                onFavoriteClick = {},
            )
        }
    }
}