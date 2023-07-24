package com.example.inuphonebook.Retrofit

import com.example.inuphonebook.Model.RetrofitDto.ProfessorDetailResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PhoneBookInterface {
    @GET("department/{departmentId}/professor/{professorId}")
    fun getDescription(@Path("departmentId") departmentId : Long, @Path("professorId") professorId : Long) : Call<ProfessorDetailResult>
    
    @GET("search/{keyword}")
    fun search(@Path("keyword") keyword : String) //아직 받는 형식 미정
}