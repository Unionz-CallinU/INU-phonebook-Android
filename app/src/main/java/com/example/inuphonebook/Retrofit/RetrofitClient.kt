package com.example.inuphonebook.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val phoneBookInterface : PhoneBookInterface
    private const val baseUrl = "https://b05abb42-ed16-4e8f-a034-39ce4655c5e5.mock.pstmn.io/api/"

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