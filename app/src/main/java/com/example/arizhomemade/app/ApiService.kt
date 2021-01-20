package com.example.arizhomemade.app

import com.example.arizhomemade.model.ResponModel
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Field
import retrofit2.Call
import retrofit2.http.GET


interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("phone") noTelepon : String,
        @Field("password") password : String
    ):Call<ResponModel>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ):Call<ResponModel>


    @GET("produk")
    fun getProduk():Call<ResponModel>
}