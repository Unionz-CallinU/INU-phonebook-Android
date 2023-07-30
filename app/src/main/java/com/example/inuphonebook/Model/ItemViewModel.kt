package com.example.inuphonebook.Model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.LocalDB.FavCategory
import com.example.inuphonebook.LocalDB.RoomRepository
import com.example.inuphonebook.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemViewModel(context : Context) : ViewModel() {
    val TAG = "ItemViewModel"

    private val roomRepo : RoomRepository = RoomRepository.get(context)

    //즐겨찾기 리스트
    private val _categoryList : MutableLiveData<MutableList<FavCategory>> = MutableLiveData<MutableList<FavCategory>>(mutableListOf())

    val categoryList : LiveData<MutableList<FavCategory>>
        get() = _categoryList

    //임시 더미 데이터
    private val tmpList1 = mutableListOf<Employee>()

    //임원진 리스트
    private val _employeeDatas : MutableLiveData<MutableList<Employee>> = MutableLiveData(tmpList1)

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
    suspend fun search(
        content : String
    ){
        viewModelScope.launch(Dispatchers.IO){
            launch{
                val results = mutableListOf<Employee>()//서버에서 데이터를 받음
                submitList(results)
            }
        }
    }

    //데이터 갱신
    private fun submitList(list : MutableList<Employee>){
        tmpList1.clear()
        tmpList1.addAll(list)
    }

    //fav employee list 갱신
    fun fetchFavEmployee(){
        viewModelScope.launch(Dispatchers.IO){
            val tmpList = roomRepo.getFavEmployee()
            //test
            tmpList.add(Employee(
                name = "서호준",
                role = "학생",
                position = "학부 연구생",
                phoneNumber = "010-6472-3783",
                isFavorite = true,
                photo = R.drawable.splash_logo,
                id = 0,
                department_name = "컴퓨터 공학부",
                college_name = "정보통신대학",
                email = "seohojon@naver.com",
                category = "기본"
            ))
            Log.d(TAG, "tmpList = ${tmpList}")
            _favEmployeeDatas.postValue(tmpList)
        }
    }
    //fav employee list에 값 추가
    fun insertEmployee(employee : Employee){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.insertEmployee(employee)
        }
    }
    //fav employee 삭제
    fun deleteEmployee(id : Int){
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
}