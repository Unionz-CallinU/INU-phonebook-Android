package inuphonebook.Component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp

@Composable
fun Logo(
    height : Dp,
    width : Dp,
    logoIcon : Int,
    colorFilter : ColorFilter? = null
){
    Image(
        modifier = Modifier
            .height(height)
            .width(width),
        painter = painterResource(logoIcon),
        contentDescription = "Logo Image",
        colorFilter = colorFilter
    )
}