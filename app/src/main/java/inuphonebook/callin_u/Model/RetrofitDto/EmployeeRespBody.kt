package inuphonebook.Model.RetrofitDto

import com.google.gson.annotations.SerializedName

data class EmployeeRespBody(
    @SerializedName("code")
    val code : Int,
    @SerializedName("data")
    val data : EmployeeDtoList,
    @SerializedName("msg")
    val msg : String
)
