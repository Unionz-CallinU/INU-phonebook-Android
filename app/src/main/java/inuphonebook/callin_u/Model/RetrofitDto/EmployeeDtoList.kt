package inuphonebook.Model.RetrofitDto

import com.google.gson.annotations.SerializedName

data class EmployeeDtoList(
    @SerializedName("employeeDtoList")
    val employeeDtoList : List<EmployeeDto>
)