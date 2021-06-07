package com.dicoding.wecare

import com.dicoding.wecare.models.LoginResponse
import com.dicoding.wecare.models.PengaduanNotOk
import com.dicoding.wecare.models.PengaduanOk
import com.dicoding.wecare.models.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Api {

//    @FormUrlEncoded
//    @POST("register")
//    fun createUser(
//        @Field("email") email:String,
//        @Field("name") name:String,
//        @Field("password") password:String,
//        @Field("school") school:String
//    ):Call<DefaultResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("nik") nik: String,
        @Field("password") password: String)
    :Call<LoginResponse>


    @GET("users")
    suspend fun Users(): Call<ResponseBody>

    @GET("detail")
    fun getDataFromAPI(@Query("id")query: Int): Call<ResponseBody>?

    @GET("pengaduanOk")
    fun getOk(@Query("id")query: Int): Call<PengaduanOk>?

    @GET("pengaduanNotOk")
    fun getNotOk(@Query("id")query: Int): Call<PengaduanNotOk>?

}