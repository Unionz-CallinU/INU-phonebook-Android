package inuphonebook.callin_u

import android.content.Context
import android.widget.Toast

//message 띄우기
fun showToast(context : Context, msg : String,){
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}

//유효성 검사 (특수 문자를 방지할 수 있도록)
fun checkInput(text : String) : Boolean{
    text.forEach{ ch ->
        if (!ch.isLetterOrDigit()) return false
    }
    return true
}