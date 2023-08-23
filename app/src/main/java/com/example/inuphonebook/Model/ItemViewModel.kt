package com.example.inuphonebook.Model

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.LocalDB.RoomRepository
import com.example.inuphonebook.Model.RetrofitDto.EmployeeDetailRespDto
import com.example.inuphonebook.Model.RetrofitDto.EmployeeReqDto
import com.example.inuphonebook.Model.RetrofitDto.EmployeeRespBody
import com.example.inuphonebook.Retrofit.RetrofitClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class ItemViewModel(context : Context) : ViewModel() {
    val TAG = "ItemViewModel"

    private val roomRepo : RoomRepository = RoomRepository.get(context)

    //즐겨찾기 리스트
    private val _categoryList : MutableLiveData<MutableList<FavCategory>> = MutableLiveData<MutableList<FavCategory>>(mutableListOf())

    val categoryList : LiveData<MutableList<FavCategory>>
        get() = _categoryList

    //임시 더미 데이터
    private val employeeList = mutableListOf<Employee>()

    //임원진 리스트
    private val _employees : MutableLiveData<MutableList<Employee>> = MutableLiveData(employeeList)

    //즐겨찾기 임원진 리스트
    private val _favEmployees : MutableLiveData<MutableList<Employee>> = MutableLiveData<MutableList<Employee>>()

    //현재 모드
    val currentMode : Flow<Int> = flow{
        emit(getCurrentMode(context))
    }

    private fun getCurrentMode(context : Context) : Int{
        return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    }

    val employees : LiveData<MutableList<Employee>>
        get() = _employees

    val favEmployees : LiveData<MutableList<Employee>>
        get() = _favEmployees

//    //검색
//    fun search(content : String) : String {
//        var resultMsg = ""
//        val employeeReqDto = EmployeeReqDto(content)
//        val call = RetrofitClient.getPhoneBookInterface().search(employeeReqDto)
//        Log.d(TAG, "call end = ${System.currentTimeMillis()}")
//        call.enqueue(object : Callback<EmployeeRespBody>{
//            override fun onResponse(
//                call: Call<EmployeeRespBody>,
//                response: Response<EmployeeRespBody>,
//            ) {
//                when (response.code()) {
//                    //응답 성공
//                    200 -> {
//                        val responseParameter = response.body() ?: throw NullPointerException("Error : Search Content is NULL on ${TAG}")
//                        //조회 성공
//                        if (responseParameter.code == 1) {
//                            Log.d(TAG,"response success = ${System.currentTimeMillis()}")
//                            resultMsg = "Success"
//                            setResult(responseParameter.data)
//                        }
//                        //조회 실패
//                        else {
//                            resultMsg = responseParameter.msg
//                        }
//                    }
//                    //요청 정상 처리 불가
//                    400 -> {
//                        resultMsg = "HttpStatus.BAD_REQUEST(400)\n요청이 정상적으로 처리되지 않음"
//                    }
//                    //해당 id에 해당하는 직원 없음
//                    404 -> {
//                        employeeList.clear()
//                        resultMsg = "Result is NULL"
//                    }
//                }
//            }
//            override fun onFailure(call: Call<EmployeeRespBody>, t: Throwable) {
//                throw IllegalArgumentException("Error : ${t.message}")
//            }
//        })
//        Log.d(TAG,"search end = ${System.currentTimeMillis()}")
//        return resultMsg
//    }

    suspend fun search(content : String) : Deferred<String> =
        viewModelScope.async(Dispatchers.IO){
            var resultMsg = ""
            val employeeReqDto = EmployeeReqDto(content)
            val call = RetrofitClient.getPhoneBookInterface().search(employeeReqDto)
            Log.d(TAG,"call end = ${System.currentTimeMillis()}")
            try {
                /** 여기서 2000ms가 걸림 <> 속도를 줄여야 해 */
                val response = call.awaitResponse()
                when (response.code()){
                    //응답 성공
                    200 -> {
                        val responseParameter = response.body() ?: throw NullPointerException("Error : Search Content is NULL on ${TAG}")
                        //조회 성공
                        if (responseParameter.code == 1){
                            resultMsg = "Success"
                            setResult(responseParameter.data)
                        }
                        //조회 실패
                        else {
                            resultMsg = responseParameter.msg
                        }
                    }
                    //요청 정상 처리 불가
                    400 -> {
                        resultMsg = "HttpStatus.BAD_REQUEST(400)\n요청이 정상적으로 처리되지 않음"
                    }
                    //해당 id에 해당하는 직원 없음
                    404 -> {
                        employeeList.clear()
                        resultMsg = "Result is NULL"
                    }
                }
            } catch (t : Throwable){
                //연결 실패 시 처리할 event
                throw IllegalArgumentException("Error : ${t.message}")
            }
            return@async resultMsg
        }

    //데이터 갱신
    private fun submitList(list : MutableList<Employee>){
        employeeList.clear()
        employeeList.addAll(list)
    }

    //fav employee list 갱신
    fun fetchFavEmployee(){
        viewModelScope.launch(Dispatchers.IO){
            val tmpList = roomRepo.getFavEmployee()
            _favEmployees.postValue(tmpList)
        }
    }
    //fav employee list에 값 추가
    fun insertEmployee(employee : Employee, category : String){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.insertEmployee(employee)
            roomRepo.updateEmployeeCategory(employee.id, category)
            roomRepo.updateEmployee(employee.id, true)
            fetchFavEmployee()
        }
    }
    //fav employee 삭제
    fun deleteEmployee(id : Long){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.deleteEmployee(id)
            fetchFavEmployee()
        }
    }
    //fav employee 수정
    fun updateEmployee(employee: Employee){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.updateEmployee(employee.id, employee.isFavorite)
        }
    }

    //employee의 category 수정
    fun updateEmployeeCategory(employee : Employee, category : String){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.updateEmployeeCategory(employee.id, category)
        }
    }

    //employee id 검색
    fun getEmployeeById(id : Long) : Employee? {
        favEmployees.value?.forEach{
            if (it.id == id){
                return it
            }
        }
        employees.value?.forEach{
            if (it.id == id){
                return it
            }
        }
        return null
    }

    //즐겨찾기 category에 '기본'이 없다면 추가
    fun insertBasicCategoryIsNull(){
        viewModelScope.launch(Dispatchers.IO){
            val basic = roomRepo.getCategoryByName("기본")
            if (basic == null){
                roomRepo.insertCategory(FavCategory("기본"))
                fetchAllCategory()
            }
        }
    }

    //즐겨찾기 category 초기화 & 갱신
    fun fetchAllCategory(){
        viewModelScope.launch(Dispatchers.IO){
            val categoryList = roomRepo.getAllCategory()
            _categoryList.postValue(categoryList)
        }
    }

    //category 추가
    fun insertCategory(category : FavCategory){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.insertCategory(category)
        }
    }

    //category 삭제
    fun deleteCategory(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepo.deleteCategory(id)
        }
    }

    //받은 데이터를 정리해서 observed되는 리스트에 setting
    fun setResult(result : List<EmployeeDetailRespDto>){
        val tmpList = mutableListOf<Employee>()
        result.forEach{employee ->
            //test용 >> Json이 1L과 같은 형식으로 오고 있음
            val newId = (employee.id).substring(0..0).toLong()
            val isFavorite = favEmployees.value?.any { it.id == newId} ?: throw NullPointerException("Error : FavEmployeeDatas is NULL on ${TAG}")

            val newEmployee = Employee(
                category = null,
                name = employee.name,
                role = employee.role ?: "-",
                phoneNumber = employee.phoneNumber,
                isFavorite = isFavorite,
                photo = "",
                email = employee.email ?: "-",
                college_name = employee.college,
                department_name = employee.department ?: "-",
                id = newId
            )
            tmpList.add(newEmployee)
        }
        submitList(tmpList)
    }
}