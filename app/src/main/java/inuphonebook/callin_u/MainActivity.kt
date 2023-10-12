package inuphonebook

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import inuphonebook.Model.ItemViewModel
import inuphonebook.ui.theme.INUPhoneBookTheme

class MainActivity : ComponentActivity() {

    //ui상태 확인 manager
    private lateinit var uiModeManager : UiModeManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager

        setContent {
            INUPhoneBookTheme {

                //itemViewModel initialize
                val context = LocalContext.current
                val itemViewModel = ItemViewModel(context)

                //RoomDB category 초기 설정
                itemViewModel.insertBasicCategoryIsNull()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PhoneBookApp(itemViewModel)
                }
            }
        }
    }
}