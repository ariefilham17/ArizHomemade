package com.example.arizhomemade.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.arizhomemade.R
import com.example.arizhomemade.helper.SharedPref
import kotlinx.android.synthetic.main.activity_masuk.*
import kotlinx.android.synthetic.main.activity_register.btn_register

class MasukActivity : AppCompatActivity() {

    lateinit var s:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_masuk)

        mainBtn()
    }

    fun mainBtn(){
        s = SharedPref(this)


        btn_proseslogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        btn_register.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}