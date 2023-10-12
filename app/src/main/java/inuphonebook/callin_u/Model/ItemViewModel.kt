package inuphonebook.Model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.TypeConverters
import inuphonebook.LocalDB.Employee
import inuphonebook.LocalDB.FavCategory
import inuphonebook.LocalDB.RoomRepository
import inuphonebook.Model.RetrofitDto.EmployeeDto
import inuphonebook.Retrofit.RetrofitClient
import inuphonebook.callin_u.showToast
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    //data 로딩 상태
    private val _isLoading = mutableStateOf(true)
    val isLoading : State<Boolean> = _isLoading

    //검색
    suspend fun search(content : String) : String =
        viewModelScope.async(Dispatchers.IO){

            var resultMsg = ""
            val call = RetrofitClient.getPhoneBookInterface().search(content)

            try {
                _isLoading.value = false

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

                _isLoading.value = true

            } catch (t : Throwable){
                //연결 실패 시 처리할 event
                throw IllegalArgumentException("Error : ${t.message}")
            }
            return@async resultMsg
        }.await()

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
                role = employee.role,
                phoneNumber = employee.phoneNumber,
                isFavorite = true,
                email = employee.email,
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
    suspend fun updateEmployeeCategory(employee : Employee, category : String) =
        viewModelScope.async(Dispatchers.IO){
            return@async roomRepo.updateEmployeeCategory(employee.id, category)
        }.await()

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
    fun insertCategory(chDialog : () -> Unit, context : Context, category : FavCategory){
        viewModelScope.launch(Dispatchers.IO){
            if (checkDupCategory(category.category)){
                roomRepo.insertCategory(category)
                chDialog()
            } else {
                withContext(Dispatchers.Main){
                    showToast(context, "해당 카테고리는 이미 존재합니다.")
                }
            }
        }
    }

    //category 삭제
    fun deleteCategory(id : Int){
        viewModelScope.launch(Dispatchers.IO) {
            roomRepo.deleteCategory(id)
        }
    }

    //서버에서 받은 데이터 세팅
    private fun setResult(result : List<EmployeeDto>){
        val tmpList = mutableListOf<Employee>()
        result.forEach{employee ->
            //서버에서 받아온 데이터와 로컬에 저장된 데이터를 비교 >> 즐겨찾기 확인
            val isFavorite = favEmployees.value?.any { it.id == employee.id} ?: throw NullPointerException("Error : FavEmployeeDatas is NULL on ${TAG}")

            val photoUrl = if(employee.imageUrl == "null") null else employee.imageUrl

            val phoneNumber = if(employee.phoneNumber == "null" || employee.phoneNumber == null || employee.phoneNumber == "--") null else employee.phoneNumber

            val newEmployee = Employee(
                category = null,
                name = employee.name,
                role = employee.position ?: "-",
                phoneNumber = phoneNumber ?: "-",
                isFavorite = isFavorite,
                photo = photoUrl,
                email = employee.email ?: "-",
                college_name = employee.college,
                department_name = employee.department ?: "-",
                id = employee.id
            )
            tmpList.add(newEmployee)
        }
        submitList(tmpList)
    }

    //category안에 데이터가 있는지 확인
    suspend fun isEmployeeInCategory(category : String) : Boolean =
        viewModelScope.async(Dispatchers.IO){
            return@async roomRepo.getEmployeesInCategory(category) != 0
        }.await()

    //category 중복 확인
    suspend fun checkDupCategory(category : String) =
        viewModelScope.async(Dispatchers.IO){
            val favCategory = roomRepo.checkDupCategory(category)
            return@async favCategory == null
        }.await()
}