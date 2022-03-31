package com.example.mymap.tasks

import android.location.Address
import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.MutableLiveData

class GeoCoderTask(
    private val liveGeo: MutableLiveData<Address>,
    private val geoCoder: Geocoder,
    private val location: Location
) : Thread() {
    override fun run() {
        super.run()
        val address = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
        if (address.isNotEmpty()) {
            val loc = address[0]
            liveGeo.postValue(loc)
        }
    }
}