package inuphonebook.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import inuphonebook.R
import inuphonebook.ui.theme.Black
import inuphonebook.ui.theme.Blue
import inuphonebook.ui.theme.White
import inuphonebook.ui.theme.Gray4

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCategoryDialog(
    modifier : Modifier = Modifier,
    onDismissRequest : () -> Unit,
    properties : DialogProperties = DialogProperties(),
    title : String = "title",
    okMsg : String = "okMsg",
    onAddClick : () -> Unit = {},
    value : String = "",
    onChangeValue : (String) -> Unit,
    fontFamily : FontFamily = FontFamily(Font(R.font.pretendard_medium))
) {
    val textColor = if(isSystemInDarkTheme()) White else Black
    val dialogBackground = if(isSystemInDarkTheme()) Gray4 else White

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ){
        Column(
            modifier = modifier
                .background(
                    color = dialogBackground,
                    shape = RoundedCornerShape(size = 10.dp)
                )
                .clip(shape = RoundedCornerShape(size = 10.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(Modifier.height(10.dp))

            TextField(
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        color = dialogBackground,
                        shape = RoundedCornerShape(size = 20.dp)
                    ),
                placeholder = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = title,
                        fontSize = 16.sp,
                        color = textColor,
                        fontFamily = fontFamily,
                        letterSpacing = 1.sp,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = dialogBackground,
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
                    fontFamily = fontFamily,
                    letterSpacing = 1.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}