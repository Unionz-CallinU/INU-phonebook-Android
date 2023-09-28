package inuphonebook.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    
    private val phoneBookInterface : PhoneBookInterface
    private const val baseUrl = "https://callinu.inuappcenter.kr"

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