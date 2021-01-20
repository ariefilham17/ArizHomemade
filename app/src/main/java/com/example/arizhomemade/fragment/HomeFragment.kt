package com.example.arizhomemade.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.arizhomemade.R
import com.example.arizhomemade.adapter.AdapterProduk
import com.example.arizhomemade.app.ApiConfig
import com.example.arizhomemade.model.Produk
import com.example.arizhomemade.model.ResponModel
import com.inyongtisto.tutorial.adapter.AdapterSlider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.os.Bundle as Bundle1


class HomeFragment : Fragment() {

    lateinit var vpslider: ViewPager
    lateinit var rvProduk: RecyclerView
    lateinit var rvLaris: RecyclerView
    lateinit var rvCemilan: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle1?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home,container,false)
        init(view)
        getProduk()

        return view
    }

    private var listProduk:ArrayList<Produk> = ArrayList()

    fun getProduk(){
        ApiConfig.instanceRetrofit.getProduk().enqueue(object : Callback<ResponModel> {
            override fun onFailure(call: Call<ResponModel>, t: Throwable) {

            }

            override fun onResponse(call: Call<ResponModel>, response: Response<ResponModel>) {
                val res = response.body()!!
                if (res.success == 1) {
                    listProduk = res.produks
                    displayProduk()
                }
            }
        })
    }

    fun init(view: View) {
        vpslider = view.findViewById(R.id.vpSlider)
        rvProduk = view.findViewById(R.id.rvProdukBaru)
        rvLaris = view.findViewById(R.id.rvProdukLaris)
        rvCemilan = view.findViewById(R.id.rvCemilan)
    }

    fun displayProduk(){
        val arrSlider = ArrayList<Int>()
        arrSlider.add(R.drawable.banner1)
        arrSlider.add(R.drawable.banner2)
        arrSlider.add(R.drawable.banner3)

        val adapterSlider = AdapterSlider(arrSlider, activity)
        vpslider.adapter= adapterSlider

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManagerLaris = LinearLayoutManager(activity)
        layoutManagerLaris.orientation = LinearLayoutManager.HORIZONTAL

        val layoutManagerCemilan = LinearLayoutManager(activity)
        layoutManagerCemilan.orientation = LinearLayoutManager.HORIZONTAL

        rvProduk.adapter = AdapterProduk(requireActivity(), listProduk)
        rvProduk.layoutManager = layoutManager

        rvLaris.adapter = AdapterProduk(requireActivity(), listProduk)
        rvLaris.layoutManager = layoutManagerLaris

        rvCemilan.adapter = AdapterProduk(requireActivity(), listProduk)
        rvCemilan.layoutManager = layoutManagerCemilan
    }
}