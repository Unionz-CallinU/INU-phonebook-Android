package com.example.inuphonebook.Model

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inuphonebook.LocalDB.Employee
import com.example.inuphonebook.LocalDB.Professor
import com.example.inuphonebook.LocalDB.RoomDB
import com.example.inuphonebook.LocalDB.RoomDao
import com.example.inuphonebook.LocalDB.RoomRepository
import com.example.inuphonebook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ItemViewModel(context : Context) : ViewModel() {

    private val roomRepo : RoomRepository = RoomRepository.get(context)

    //임시 더미 데이터
    private val tmpList1 = mutableListOf<Employee>()
    private val tmpList2 = mutableListOf<Professor>()


    //임원진 리스트
    private val _employeeDatas : MutableLiveData<MutableList<Employee>> = MutableLiveData(tmpList1)
    //교수진 리스트
    private val _professorDatas : MutableLiveData<MutableList<Professor>> = MutableLiveData(tmpList2)
    //즐겨찾기 교수 리스트
    //private val _favProfessorDatas : MutableLiveData<List<Professor>> = MutableLiveData<List<Professor>>()
    //test
    private val _favProfessorDatas : MutableLiveData<List<Professor>> = MutableLiveData<List<Professor>>(listOf(Professor(name = "서호준", phone = "010-6472-3783", email = "seohojon@naver.com", photo = R.drawable.ic_launcher_background, lab = "7호관 414호", isFavorite = true, id = 0)))
    //즐겨찾기 임원진 리스트
    private val _favEmployeeDatas : MutableLiveData<List<Employee>> = MutableLiveData<List<Employee>>()

    val employeeDatas : LiveData<MutableList<Employee>>
        get() = _employeeDatas

    val professorDatas : LiveData<MutableList<Professor>>
        get() = _professorDatas

    val favProfessorDatas : LiveData<List<Professor>>
        get() = _favProfessorDatas

    val favEmployeeDatas : LiveData<List<Employee>>
        get() = _favEmployeeDatas


    //실험용 dummy test 기본을 NULL로 주고 데이터를 받자
    private val _selectedItem = mutableStateOf<Any>(
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "Test1",
            department = "컴퓨터 공학부",
            phone = "010-xxxx-xxxx",
            favorite = false
        )
    )
    val selectedItem : State<Any?> get() = _selectedItem

    //선택된 item을 지정하여 description 시 이용
    fun setSelectedItem(item : Any){
        _selectedItem.value = item
    }

    //검색
    suspend fun search(
        content : String
    ){
        coroutineScope{
            launch{
                val results = mutableListOf<Any>()//서버에서 데이터를 받음
                submitList(results)
            }
        }
    }

    //데이터 갱신
    private fun submitList(list : MutableList<Any>){
        tmpList1.clear()
        tmpList2.clear()
    }

    /** RoomDB에서 데이터 받아오기 */
    //fav professor list 받아오기
    fun getFavProfessor(){
        viewModelScope.launch(Dispatchers.IO){
            val tmpList = roomRepo.getFavProfessors()
            _favProfessorDatas.postValue(tmpList)
        }
    }
    //fav professor list에 값 추가
    fun insertProfessor(professor : Professor){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.insertProfessor(professor)
        }
    }
    //fav professor 삭제
    fun deleteProfessor(id : Int){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.deleteProfessor(id)
        }
    }
    fun updateProfessor(professor: Professor){
        viewModelScope.launch(Dispatchers.IO){
            roomRepo.updateProfessor(professor.id, professor.isFavorite)
        }
    }
    //fav employee list 받아오기
    fun getFavEmployee(){
        viewModelScope.launch(Dispatchers.IO){
            val tmpList = roomRepo.getFavEmployee()
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
}