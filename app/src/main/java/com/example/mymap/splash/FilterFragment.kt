package com.example.mymap.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymap.R
import com.example.mymap.SpinnerAdapter
import com.example.mymap.constants.Constants.PROVINCES
import com.example.mymap.constants.Constants.TASHKENT_UNIVERSITY
import com.example.mymap.constants.Constants.gubkina
import com.example.mymap.data.TTJLocation
import com.example.mymap.listener.OnItemSelectListener
import kotlinx.android.synthetic.main.fragment_filter.*

class FilterFragment : Fragment() {

    private var viloyatId = -1
    private var universitetId = -1
    private var ttjId = -1
    private val dialog = SpinnerAdapter()
    private var ttjText = "TTJ"

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
                2 -> {

                }
                3 -> {

                }
                4 -> {

                }
            }
        }


    }

    private fun selectCountryItem(array: Array<String>) {
        val listener = object : OnItemSelectListener {
            override fun onItemSelected(id: Int) {
                if (id > -1) {
                    ttj.isEnabled = true
                    universitetId = id
                    //todo if you have location another university after check to do it
                    universitet.text = array[id]
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
                    ttj.text = myList[id].name

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