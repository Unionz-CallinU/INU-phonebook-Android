package com.example.inuphonebook.Model

import android.content.Context
import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ItemViewModel(context : Context) : ViewModel() {
    val TAG = "ItemViewModel"

    private val roomRepo : RoomRepository = RoomRepository.get(context)

    //즐겨찾기 리스트
    private val _categoryList : MutableLiveData<MutableList<FavCategory>> = MutableLiveData<MutableList<FavCategory>>(mutableListOf())

    val categoryList : LiveData<MutableList<FavCategory>>
        get() = _categoryList

    //임시 더미 데이터
    private val employeeList = mutableListOf<Employee>(
        Employee(
            name = "서호준",
            role = "학생",
            phoneNumber = "010-6472-3783",
            isFavorite = false,
            photo = null,
            id = 1,
            department_name = "컴퓨터 공학부",
            college_name = "정보통신대학",
            email = "seohojon@naver.com",
            category = "기본"
        )
    )

    //임원진 리스트
    private val _employeeDatas : MutableLiveData<MutableList<Employee>> = MutableLiveData(employeeList)

    //즐겨찾기 임원진 리스트
    private val _favEmployeeDatas : MutableLiveData<MutableList<Employee>> = MutableLiveData<MutableList<Employee>>()

    val employeeDatas : LiveData<MutableList<Employee>>
        get() = _employeeDatas

    val favEmployeeDatas : LiveData<MutableList<Employee>>
        get() = _favEmployeeDatas


    //실험용 dummy test 기본을 NULL로 주고 데이터를 받자
    private val _selectedItem = mutableStateOf<Employee?>(null)
    val selectedItem : State<Employee?> get() = _selectedItem

    //선택된 item을 지정하여 description 시 이용
    fun setSelectedItem(employee : Employee){
        _selectedItem.value = employee
    }

    //검색
    fun search(content : String) : String{
        var resultMsg = ""
        val employeeReqDto = EmployeeReqDto(content)
        val call = RetrofitClient.getPhoneBookInterface().search(employeeReqDto)
        call.enqueue(object : Callback<EmployeeRespBody>{
            override fun onResponse(
                call: Call<EmployeeRespBody>,
                response: Response<EmployeeRespBody>,
            ) {
                val httpStatusCode = response.code()
                when (httpStatusCode){
                    //응답 성공
                    200 -> {
                        val responseParameter = response.body() ?: throw NullPointerException("Search Content is NULL")
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
            }

            override fun onFailure(call: Call<EmployeeRespBody>, t: Throwable) {
                //연결 실패 시 처리할 event
                throw IllegalArgumentException("Error : ${t.message}")
            }
        })
        return resultMsg
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
            _favEmployeeDatas.postValue(tmpList)
        }
    }
    //fav employee list에 값 추가
    fun insertEmployee(employee : Employee){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.insertEmployee(employee)
            roomRepo.updateEmployee(employee.id, true)
            _selectedItem.value = employee
        }
    }
    //fav employee 삭제
    fun deleteEmployee(id : Long){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.deleteEmployee(id)
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
        _selectedItem.value = employee
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
            val isFavorite = favEmployeeDatas.value?.any { it.id == employee.id} ?: throw NullPointerException("FavEmployeeDatas is NULL")

            val newEmployee = Employee(
                category = null,
                name = employee.name,
                role = employee.role,
                phoneNumber = employee.phoneNumber,
                isFavorite = isFavorite,
                photo = employee.photo,
                email = employee.email,
                college_name = employee.college,
                department_name = employee.department,
                id = employee.id
            )
            tmpList.add(newEmployee)
        }
        submitList(tmpList)
    }
}