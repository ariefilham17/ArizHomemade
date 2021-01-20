package com.example.arizhomemade.helper

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.arizhomemade.model.UserModel
import com.google.gson.Gson

class SharedPref(activity: Activity) {
    val login = "LOGIN"
    val nama = "name"
    val phone = "phone"
    val email = "email"

    val user = "user"

    val mypref = "Main_Pref"
    val sp:SharedPreferences

    init {
        sp = activity.getSharedPreferences(mypref, Context.MODE_PRIVATE)
    }

    fun setStatusLogin(status: Boolean){
        sp.edit().putBoolean(login, status).apply()
    }

    fun getStatusLogin():Boolean{
        return sp.getBoolean(login, false)
    }

    fun setUser(value: UserModel){
        val data:String = Gson().toJson(value, UserModel::class.java)
        sp.edit().putString(user, data).apply()
    }

    fun getUser() : UserModel? {
        val data = sp.getString(user, null) ?: return null
        return Gson().fromJson<UserModel>(data, UserModel::class.java)
    }

//    fun setString(key: String, value: String){
//        sp.edit().putString(login, key).apply()
//    }
//
//    fun getString(key: String): String{
//        return sp.getString(key, "")!!
//    }
}