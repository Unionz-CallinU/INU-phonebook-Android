package com.example.inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.inuphonebook.R
import com.example.inuphonebook.ui.theme.Black
import com.example.inuphonebook.ui.theme.Blue
import com.example.inuphonebook.ui.theme.INUPhoneBookTheme
import com.example.inuphonebook.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAddCategoryDialog(
    modifier : Modifier = Modifier,
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    title : String = "title",
    okMsg : String = "okMsg",
    onAddClick : () -> Unit = {},
    value : String = "",
    onChangeValue : (String) -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ){
        Column(
            modifier = modifier
                .background(color = Color.White, shape = RoundedCornerShape(size = 10.dp))
                .clip(shape = RoundedCornerShape(size = 10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(10.dp))

            TextField(
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        color = White,
                        shape = RoundedCornerShape(size = 20.dp)
                    ),
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = title,
                        fontSize = 16.sp,
                        color = Black,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        letterSpacing = 1.sp,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                value = value,
                onValueChange = onChangeValue
            )

            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .background(color = Blue)
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable {
                        onAddClick()
                        onDismissRequest()
                    },
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = okMsg,
                    fontSize = 20.sp,
                    color = White,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun TestAddDialog(){
    INUPhoneBookTheme {
        Box(
            modifier = Modifier.wrapContentSize()
        ){
            CustomAddCategoryDialog(
                onDismissRequest = {},
                properties = DialogProperties(),
                onChangeValue = {}
            )
        }
    }
}