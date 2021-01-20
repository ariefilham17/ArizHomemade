package com.example.arizhomemade.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.arizhomemade.R
import com.example.arizhomemade.helper.Helper
import com.example.arizhomemade.model.Produk
import com.example.arizhomemade.room.MyDatabase
import com.example.arizhomemade.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import kotlinx.android.synthetic.main.toolbar_custom.*


class DetailProdukActivity : AppCompatActivity() {

    lateinit var produk: Produk
    lateinit var myDb: MyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        myDb = MyDatabase.getInstance(this)!! // call database

        getInfo()
        mainButton()
        cekKeranjang()

    }

    private fun mainButton() {

        btn_keranjang.setOnClickListener{
            val data = myDb.daoKeranjang().getProduk(produk.id)
            if (data == null){
                insert()
            }else{
                data.jumlah += 1
                update(data)
            }

        }

        btn_favorit.setOnClickListener {
            val listData = myDb.daoKeranjang().getAll() // get All data
            for(note :Produk in listData){
                println("-----------------------")
                println(note.name)
                println(note.harga)
            }
        }

        btn_tokeranjang.setOnClickListener {
            val intent = Intent("event:keranjang")
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            onBackPressed()
        }
    }

    private fun insert() {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().insert(produk) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    Log.d("respons", "data inserted")
                    Toast.makeText(this,"Berhasil ditambahkan ke keranjang", Toast.LENGTH_SHORT).show()
                })
    }

    private fun update(data: Produk) {
        CompositeDisposable().add(Observable.fromCallable { myDb.daoKeranjang().update(data) }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("respons", "data inserted")
                Toast.makeText(this,"Update berhasil", Toast.LENGTH_SHORT).show()
            })
    }


    private fun cekKeranjang(){
        val datakeranjang = myDb.daoKeranjang().getAll()
        if(datakeranjang.isNotEmpty()){
            div_angka.visibility = View.VISIBLE
            txt_angka.text = datakeranjang.size.toString()
        }else{
            div_angka.visibility = View.GONE
        }
    }


    private fun getInfo() {
        val data = intent.getStringExtra("extra")
        produk = Gson().fromJson<Produk>(data, Produk::class.java)

        // set Value
        tv_nama.text = produk.name
        tv_harga.text = Helper().gantiRupiah(produk.harga)
        tv_deskripsi.text = produk.deskripsi

        val img = Config.produkURL + produk.image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.iron_man_100)
            .error(R.drawable.iron_man_100)
            .resize(400, 400)
            .into(image)

        // setToolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = produk.name
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}