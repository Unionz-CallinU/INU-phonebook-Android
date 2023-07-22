package com.example.inuphonebook.Model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.inuphonebook.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ItemViewModel {

    //임시 더미 데이터
    private val tmpList1 = mutableListOf<Item>(
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "서호준",
            department = "학과 사무실",
            phone = "010-6472-3783",
            favorite = false
        ),
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "김영희",
            department = "학과 사무실",
            phone = "010-1111-1111",
            favorite = true
        ),
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "김철수",
            department = "학과 사무실",
            phone = "010-2222-2222",
            favorite = false
        )
    )
    private val tmpList2 = mutableListOf<Item>(
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "최대진",
            department = "컴퓨터 공학부",
            phone = "010-3333-3333",
            favorite = true
        ),
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "최진석",
            department = "컴퓨터 공학부",
            phone = "010-5555-5555",
            favorite = true
        ),
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "김지범",
            department = "컴퓨터 공학부",
            phone = "010-4444-4444",
            favorite = false
        )
    )


    /** 데이터를 나눠서 받음 : 2번의 통신 필요
     *  데이터를 한 번에 받아서 구분 : 구분 짓을 type이 필요
     *  한 번에 받는 다면 받은 list를 기반으로 두 개의 list로 구분 짓는 방식이 필요     * */

    //학과 사무실 리스트
    private val _employeeDatas : MutableLiveData<MutableList<Item>> = MutableLiveData(tmpList1)
    //교수진 리스트
    private val _professorDatas : MutableLiveData<MutableList<Item>> = MutableLiveData(tmpList2)

    val employeeDatas : LiveData<MutableList<Item>>
        get() = _employeeDatas

    val professorDatas : LiveData<MutableList<Item>>
        get() = _professorDatas

    //실험용 dummy test 기본을 NULL로 주고 데이터를 받자
    private val _selectedItem = mutableStateOf<Item>(
        Item(
            image = R.drawable.ic_launcher_foreground,
            name = "Test1",
            department = "컴퓨터 공학부",
            phone = "010-xxxx-xxxx",
            favorite = false
        )
    )
    val selectedItem : State<Item?> get() = _selectedItem

    //선택된 item을 지정하여 description 시 이용
    fun setSelectedItem(item : Item){
        _selectedItem.value = item
    }

    //검색
    suspend fun search(
        content : String
    ){
        coroutineScope{
            launch{
                val results = mutableListOf<Item>()//서버에서 데이터를 받음
                submitList(results)
            }
        }
    }

    //데이터 갱신
    private fun submitList(list : MutableList<Item>){
        tmpList1.clear()
        tmpList2.clear()
    }

}