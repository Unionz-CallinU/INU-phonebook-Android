package com.example.inuphonebook.Model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ItemViewModel {

    private val tmpList = mutableListOf<Item>()

    private val _datas : MutableLiveData<MutableList<Item>> = MutableLiveData(tmpList)

    val datas : LiveData<MutableList<Item>>
        get() = _datas

    private val _selectedItem = mutableStateOf<Item?>(null)
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
        tmpList.clear()
        tmpList.addAll(list)
    }

}