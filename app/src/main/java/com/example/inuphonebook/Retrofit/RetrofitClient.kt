package com.example.inuphonebook.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val phoneBookInterface : PhoneBookInterface
    //아직 https 아님 https가 되면 manifest networkSecurityConfig 설정을 지우고 xml 파일 삭제
    private const val baseUrl = "http://ec2-43-201-168-104.ap-northeast-2.compute.amazonaws.com"

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