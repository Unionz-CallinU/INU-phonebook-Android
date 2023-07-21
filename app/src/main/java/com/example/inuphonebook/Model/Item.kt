package com.example.inuphonebook.Model

//서버의 API를 보고 Data Class를 구성
data class Item(
    val image : Int, //임시
    val name : String,
    val department : String,
    val phone : String,
    val favorite : Boolean,
)