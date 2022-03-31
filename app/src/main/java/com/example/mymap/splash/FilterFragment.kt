package com.example.mymap.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mymap.R
import com.example.mymap.SpinnerAdapter
import com.example.mymap.constants.Constants
import com.example.mymap.constants.Constants.PROVINCES
import com.example.mymap.constants.Constants.TASHKENT_UNIVERSITY
import com.example.mymap.constants.Constants.gubkina
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
            val listener = object : OnItemSelectListener {
                override fun onItemSelected(id: Int) {
                    if (id > -1) {
                        ttj.isEnabled = true
                        universitetId = id
                        //todo if you have location another university after check to do it
                        universitet.text = TASHKENT_UNIVERSITY[0]
                    }
                }
            }
            when (viloyatId) {
                0 -> {
                    selectFilter("Universitet", Constants.TASHKENT_UNIVERSITY, listener)
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
            val listener = object : OnItemSelectListener {
                override fun onItemSelected(id: Int) {
                    if (id > -1) {
                        ttjId = id
                        btn_topish.isEnabled = true
                        ttj.text = ttjText
                    }
                }
            }
            when (universitetId) {
                0 -> {
                    val list = Array(gubkina.size) { i -> "" }
                    for (i in gubkina.indices) {
                        list[i] = gubkina[i].name
                    }
                    selectFilter("Gubkina TTJ", list, listener)
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