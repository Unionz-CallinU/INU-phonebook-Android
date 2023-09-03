package com.example.inuphonebook.Model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.TypeConverters
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.LocalDB.RoomRepository
import com.example.inuphonebook.Model.RetrofitDto.EmployeeDto
import com.example.inuphonebook.Model.RetrofitDto.EmployeeReqDto
import com.example.inuphonebook.Retrofit.RetrofitClient
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

@TypeConverters
class ItemViewModel(context : Context) : ViewModel() {
    val TAG = "ItemViewModel"

    //Repository 초기화
    private val roomRepo : RoomRepository = RoomRepository.get(context)

    //즐겨찾기 카테고리 list
    private val _categoryList : MutableLiveData<MutableList<FavCategory>> = MutableLiveData<MutableList<FavCategory>>(mutableListOf())

    val categoryList : LiveData<MutableList<FavCategory>>
        get() = _categoryList

    //서버에서 받은 데이터 Local에 Cache 저장
    private val employeeList = mutableListOf<Employee>()

    private val _employees : MutableLiveData<MutableList<Employee>> = MutableLiveData(employeeList)

    val employees : LiveData<MutableList<Employee>>
        get() = _employees

    //즐겨찾기 리스트
    private val _favEmployees : MutableLiveData<MutableList<Employee>> = MutableLiveData<MutableList<Employee>>()

    val favEmployees : LiveData<MutableList<Employee>>
        get() = _favEmployees
    
    //검색
    suspend fun search(content : String) : Deferred<String> =
        viewModelScope.async(Dispatchers.IO){

            var resultMsg = ""
            val employeeReqDto = EmployeeReqDto(content)
            val call = RetrofitClient.getPhoneBookInterface().search(employeeReqDto)

            try {
                val response = call.awaitResponse()
                resultMsg = when (response.code()){
                    //응답 성공
                    200 -> {
                        val responseParameter = response.body() ?: throw NullPointerException("Error : Search Content is NULL on ${TAG}")
                        //조회 성공
                        if (responseParameter.code == 1){
                            setResult(responseParameter.data.employeeDtoList)
                        }
                        responseParameter.msg
                    }
                    //요청 정상 처리 불가
                    else -> {
                        response.body()!!.msg
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
            val newEmployee = Employee(
                category = category,
                name = employee.name,
                phoneNumber = employee.phoneNumber,
                isFavorite = true,
                photo = employee.photo,
                college_name = employee.college_name,
                department_name = employee.department_name,
                id = employee.id
            )
            roomRepo.insertEmployee(newEmployee)
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

    //employeeList isFavorite 수정
    fun updateFavorite(id : Long){
        employeeList.forEach{
            if(it.id == id){
                it.isFavorite = false
            }
        }
    }

    //Local 데이터의 category 수정
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

    //즐겨찾기 category 데이터 갱신
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

    //서버에서 받은 데이터 세팅
    fun setResult(result : List<EmployeeDto>){
        val tmpList = mutableListOf<Employee>()
        result.forEach{employee ->
            //서버에서 받아온 데이터와 로컬에 저장된 데이터를 비교 >> 즐겨찾기 확인
            val isFavorite = favEmployees.value?.any { it.id == employee.id} ?: throw NullPointerException("Error : FavEmployeeDatas is NULL on ${TAG}")

            val photoUrl = if(employee.imageUrl == null) null else employee.imageUrl.toString()
            val phoneNumber = if(employee.phoneNumber == "null" || employee.phoneNumber == null) null else employee.phoneNumber

            val newEmployee = Employee(
                category = null,
                name = employee.name,
//                role = employee.role ?: "-",
                phoneNumber = phoneNumber ?: "-",
                isFavorite = isFavorite,
                photo = photoUrl,
//                email = employee.email ?: "-",
                college_name = employee.college,
                department_name = employee.department ?: "-",
                id = employee.id
            )
            tmpList.add(newEmployee)
        }
        submitList(tmpList)
    }
}