package com.example.mymap.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymap.MapsActivity
import com.example.mymap.R
import com.example.mymap.adapter.SpinnerAdapter
import com.example.mymap.constants.Constants.Iqtisodiyot
import com.example.mymap.constants.Constants.PROVINCES
import com.example.mymap.constants.Constants.TASHKENT_UNIVERSITY
import com.example.mymap.constants.Constants.arxitekturaQurilish
import com.example.mymap.constants.Constants.axborotTexnalogiyalari
import com.example.mymap.constants.Constants.diplomatiya
import com.example.mymap.constants.Constants.farmatsevtika
import com.example.mymap.constants.Constants.gubkina
import com.example.mymap.constants.Constants.irregatsiya
import com.example.mymap.constants.Constants.jahonTillari
import com.example.mymap.constants.Constants.jurnalistika
import com.example.mymap.constants.Constants.kimyoTexnologiya
import com.example.mymap.constants.Constants.konservatoriyasi
import com.example.mymap.constants.Constants.milliy
import com.example.mymap.constants.Constants.milliy_rassomchilik
import com.example.mymap.constants.Constants.moliya
import com.example.mymap.constants.Constants.pedagogika
import com.example.mymap.constants.Constants.pediaterya
import com.example.mymap.constants.Constants.sanatMadaniyat
import com.example.mymap.constants.Constants.sharqshunoslik
import com.example.mymap.constants.Constants.stomatologiya
import com.example.mymap.constants.Constants.texnika
import com.example.mymap.constants.Constants.tibbiyotAkademiyasi
import com.example.mymap.constants.Constants.toqimachilik
import com.example.mymap.constants.Constants.transport
import com.example.mymap.constants.Constants.xalqaroIslomAkademiyasi
import com.example.mymap.constants.Constants.yuridik
import com.example.mymap.constants.Constants.yuridikIqtisoslashgan
import com.example.mymap.data.TTJLocation
import com.example.mymap.listener.OnItemSelectListener
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : Fragment() {

    private var viloyatId = -1
    private var universitetId = -1
    private var ttjId = -1
    private val dialog = SpinnerAdapter()
    private var name = "TTJ"
    private lateinit var location: LatLng

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_filter, container, false)
    }

    override fun onResume() {
        super.onResume()

        viloyat.setOnClickListener {
            val listener = object : OnItemSelectListener {
                override fun onItemSelected(id: Int) {
                    this@FilterFragment.viloyatId = id
                    if (id > -1) {
                        universitet.isEnabled = true
                        viloyat.text = PROVINCES[id]
                        universitet.text = "Universitet"
                    }
                }
            }
            selectFilter("Viloyat", PROVINCES, listener)
        }

        universitet.setOnClickListener {

            when (viloyatId) {
                0 -> {
                    selectCountryItem(TASHKENT_UNIVERSITY)

                }
                1 -> {
//todo 13 province
                }
                2 -> {

                }
                3 -> {

                }
                4 -> {

                }
                5 -> {

                }
                6 -> {

                }
            }
        }

        ttj.setOnClickListener {
            when (universitetId) {
                0 -> {
                    setFieldTTJItem("Gubkina", gubkina)
                }
                1 -> {
                    setFieldTTJItem("Diplomatiya", diplomatiya)
                }
                2 -> {
                    setFieldTTJItem("Rassomchilik", milliy_rassomchilik)
                }
                3 -> {
                    setFieldTTJItem("TATU", axborotTexnalogiyalari)
                }
                4 -> {
                    setFieldTTJItem("Irrigatsiya", irregatsiya)
                }
                5 -> {
                    setFieldTTJItem("Pediateria", pediaterya)
                }
                6 -> {
                    setFieldTTJItem("Transport", transport)
                }
                7 -> {
                    setFieldTTJItem("Yuridik", yuridik)
                }
                8 -> {
                    setFieldTTJItem("Yuridik ixtisoslashgan", yuridikIqtisoslashgan)
                }
                9 -> {
                    setFieldTTJItem("Texnika", texnika)
                }
                10 -> {
                    setFieldTTJItem("Tibbiyot akademiyasi", tibbiyotAkademiyasi)
                }
                11 -> {
                    setFieldTTJItem("Moliya", moliya)
                }
                12 -> {
                    setFieldTTJItem("Kimyo texnologiya", kimyoTexnologiya)
                }
                13 -> {
                    setFieldTTJItem("Sharqshunoslik", sharqshunoslik)
                }
                14 -> {
                    setFieldTTJItem("Pedagogika", pedagogika)
                }
                15 -> {
                    setFieldTTJItem("Textelniy", toqimachilik)
                }
                16 -> {
                    setFieldTTJItem("Arxitektura", arxitekturaQurilish)
                }
                17 -> {
                    setFieldTTJItem("Iqtisodiyor", Iqtisodiyot)
                }
                18 -> {
                    setFieldTTJItem("Stamatologiya", stomatologiya)
                }
                19 -> {
                    setFieldTTJItem("Farmasevtika", farmatsevtika)
                }
                20 -> {
                    setFieldTTJItem("Kanservatorya", konservatoriyasi)
                }
                21 -> {
                    setFieldTTJItem("Milliy", milliy)
                }
                22 -> {
                    setFieldTTJItem("Sanat madaniyat", sanatMadaniyat)
                }
                23 -> {
                    setFieldTTJItem("Jahon tillari", jahonTillari)
                }
                24 -> {
                    setFieldTTJItem("Islom akademiyasi", xalqaroIslomAkademiyasi)
                }
                25 -> {
                    setFieldTTJItem("Jurnalistika", jurnalistika)
                }

            }
        }


        btn_topish.setOnClickListener {
            val intent = Intent(activity, MapsActivity::class.java)
            intent.putExtra("location", location)
            intent.putExtra("name", name)
            startActivity(intent)
        }
    }

    private fun selectCountryItem(array: Array<String>) {
        val listener = object : OnItemSelectListener {
            override fun onItemSelected(id: Int) {
                if (id > -1) {
                    ttj.isEnabled = true
                    universitetId = id
                    universitet.text = array[id]
                    ttj.text = "TTJ"
                }
            }
        }
        selectFilter("Universitet", array, listener)
    }

    private fun setFieldTTJItem(s: String, myList: Array<TTJLocation>) {
        val listener = object : OnItemSelectListener {
            override fun onItemSelected(id: Int) {
                if (id > -1) {
                    ttjId = id
                    btn_topish.isEnabled = true
                    name = myList[id].name
                    ttj.text = name
                    location = myList[id].latLng
                }
            }
        }
        val list = Array(myList.size) { "" }
        for (i in myList.indices) {
            list[i] = myList[i].name
        }
        selectFilter(s, list, listener)
    }


    private fun selectFilter(
        s: String,
        provinces: Array<String>,
        listener: OnItemSelectListener
    ) {
        dialog.getData(s, provinces, listener)
        activity.let {
            if (it == null) return@let
            else dialog.show(
                it.supportFragmentManager,
                "asdad"
            )
        }
    }
}