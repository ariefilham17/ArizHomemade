package com.example.arizhomemade.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.arizhomemade.R
import com.example.arizhomemade.activity.DetailProdukActivity
import com.example.arizhomemade.helper.Helper
import com.example.arizhomemade.model.Produk
import com.example.arizhomemade.util.Config
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class AdapterProduk(var activity: Activity, var data:ArrayList<Produk>):RecyclerView.Adapter<AdapterProduk.Holder>() {

    class Holder(view: View): RecyclerView.ViewHolder(view){
        val tvnamaProduk = view.findViewById<TextView>(R.id.tvNamaProduk)
        val tvhargaProduk = view.findViewById<TextView>(R.id.tvHargaProduk)
        val imgproduk = view.findViewById<ImageView>(R.id.imgProduk)
        val layoutProduk = view.findViewById<CardView>(R.id.layoutProduk)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_produk,parent,false)
        return Holder(view)

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tvnamaProduk.text = data[position].name
        holder.tvhargaProduk.text = Helper().gantiRupiah(data[position].harga)
//        holder.imgproduk.setImageResource(data[position].image)

        val img = Config.produkURL + data[position].image
        Picasso.get()
            .load(img)
            .placeholder(R.drawable.iron_man_100)
            .error(R.drawable.iron_man_100)
            .into(holder.imgproduk)

                holder.layoutProduk.setOnClickListener{
                    val act = Intent(activity, DetailProdukActivity::class.java)
                    val str = Gson().toJson(data[position], Produk::class.java)
                    act.putExtra("extra", str)

                    activity.startActivity(act)
                }

    }

    override fun getItemCount(): Int {
        return data.size
    }

}