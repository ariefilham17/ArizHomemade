package com.example.arizhomemade.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.arizhomemade.R
import com.example.arizhomemade.activity.LoginActivity
import com.example.arizhomemade.helper.SharedPref

    class AkunFragment : Fragment() {

        lateinit var s:SharedPref
        lateinit var btnLogout: TextView
        lateinit var tvNama : TextView
        lateinit var tvPhone : TextView
        lateinit var tvEmail : TextView


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view: View = inflater.inflate(R.layout.fragment_akun,container,false)

            init(view)

            s = SharedPref(requireActivity())

            btnLogout.setOnClickListener {
                s.setStatusLogin(false)
            }
            setData()
            return view
        }

        fun setData(){

            if(s.getUser() == null){
                val intent = Intent(activity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                return
            }
            val user = s.getUser()!!

            tvNama.text = user.name
            tvPhone.text = user.phone
            tvEmail.text = user.email
        }

        private fun init(view: View) {
            btnLogout = view.findViewById(R.id.btn_logout)
            tvNama = view.findViewById(R.id.tv_namaUser)
            tvPhone = view.findViewById(R.id.tv_nomorUser)
            tvEmail = view.findViewById(R.id.tv_emailUser)

        }
    }
