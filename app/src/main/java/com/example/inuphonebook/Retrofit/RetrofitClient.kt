package com.example.inuphonebook.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var instance : RetrofitClient? = null
    private val phoneBookInterface : PhoneBookInterface
    private const val baseUrl = "BASE_URL"

    init{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        phoneBookInterface = retrofit.create(PhoneBookInterface::class.java)
    }

    fun getInstance() : RetrofitClient{
        if(instance == null) instance = this
        return instance ?: throw NullPointerException("RetrofitClient is NULL")
    }

    fun getPhoneBookInterface() : PhoneBookInterface{
        return phoneBookInterface
    }
}