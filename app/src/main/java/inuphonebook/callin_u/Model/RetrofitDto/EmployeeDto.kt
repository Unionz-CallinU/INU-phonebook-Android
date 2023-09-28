package inuphonebook.Model.RetrofitDto

import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("id")
    val id : Long,
    @SerializedName("name")
    val name : String,
    @SerializedName("college")
    val college : String,
    @SerializedName("phoneNumber")
    val phoneNumber : String?,
    @SerializedName("department")
    val department : String?,
    @SerializedName("role")
    val role : String?,
    @SerializedName("email")
    val email : String?,
    @SerializedName("imageUrl")
    val imageUrl : String?,
)
