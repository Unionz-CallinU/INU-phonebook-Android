package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.BlueGray

@Composable
fun CategorySpinner(
    modifier : Modifier = Modifier,
    categoryList : MutableList<FavCategory>,
    changeItem : (String) -> Unit,
    selectedCategory : String
){
    var isOpen by remember{ mutableStateOf(false) } //spinner의 상태

    Column(
        modifier = modifier.fillMaxWidth()
            .background(color = BlueGray)
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable{
                    isOpen = !isOpen
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.weight(1f),
                text = selectedCategory,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
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
                    painter = painterResource(R.drawable.spinner_dropdown_btn),
                    contentDescription = "Spinner Button"
                )
            }
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 20.dp),
            expanded = isOpen,
            onDismissRequest = {
                isOpen = !isOpen
            }
        ){
            categoryList.forEach{item ->
                DropdownMenuItem(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        changeItem(item.category)
                        isOpen = !isOpen
                    },
                    text = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = item.category,
                            textAlign = TextAlign.Center
                        )
                    }
                )
            }
        }
    }
}