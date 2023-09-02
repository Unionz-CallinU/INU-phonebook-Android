package com.example.inuphonebook.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val phoneBookInterface : PhoneBookInterface
    //아직 https 아님 https가 되면 manifest networkSecurityConfig 설정을 지우고 xml 파일 삭제
    private const val baseUrl = "http://em-todo.inuappcenter.kr"

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        phoneBookInterface = retrofit.create(PhoneBookInterface::class.java)
    }

    fun getPhoneBookInterface() : PhoneBookInterface{
        return phoneBookInterface
    }
}