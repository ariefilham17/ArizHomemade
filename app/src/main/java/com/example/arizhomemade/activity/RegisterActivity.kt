package com.example.arizhomemade.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.arizhomemade.MainActivity
import com.example.arizhomemade.R
import com.example.arizhomemade.app.ApiConfig
import com.example.arizhomemade.helper.SharedPref
import com.example.arizhomemade.model.ResponModel
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    lateinit var s: SharedPref
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        s = SharedPref(this)

        btn_register.setOnClickListener{
            register()
        }

        btn_google.setOnClickListener{
            datadummy()
        }
    }

    fun datadummy(){
        edt_nama.setText("admin")
        edt_email.setText("admin@gmail.com")
        edt_telepon.setText("0822113344")
        edt_password.setText("123456")
    }

    fun register(){
        if(edt_nama.text.isEmpty()){
            edt_nama.error = "Kolom nama tidak boleh kosong"
            edt_nama.requestFocus()
            return
        } else if(edt_email.text.isEmpty()){
            edt_email.error = "Kolom email tidak boleh kosong"
            edt_email.requestFocus()
            return
        } else if(edt_telepon.text.isEmpty()){
            edt_telepon.error = "Kolom nomor telepon tidak boleh kosong"
            edt_telepon.requestFocus()
            return
        } else if(edt_password.text.isEmpty()){
            edt_password.error = "Kolom password tidak boleh kosong"
            edt_password.requestFocus()
            return
        }

        pb.visibility = View.VISIBLE

        ApiConfig.instanceRetrofit.register(edt_nama.text.toString(), edt_email.text.toString(), edt_telepon.text.toString(), edt_password.text.toString()).enqueue(object : Callback<ResponModel> {

            override fun onFailure(call: Call<ResponModel>, t: Throwable) {
                pb.visibility = View.GONE
                Toast.makeText(this@RegisterActivity, "Error:"+ t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                pb.visibility = View.GONE
                val respon = response.body()!!

                if (respon.success == 1){
                    s.setStatusLogin(true)
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this@RegisterActivity, "Selamat datang "+respon.user.name, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this@RegisterActivity, "Error:"+ respon.message, Toast.LENGTH_SHORT).show()
                }
            }

        })

    }
}