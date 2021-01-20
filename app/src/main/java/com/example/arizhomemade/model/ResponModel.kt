package com.example.arizhomemade.model

class ResponModel {
    var success = 0
    lateinit var message:String
    var user = UserModel()
    var produks: ArrayList<Produk> = ArrayList()
}