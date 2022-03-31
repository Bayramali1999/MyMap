package com.example.mymap.tasks

import androidx.lifecycle.MutableLiveData
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.util.GeoPoint

class RoutineTask(
    private val liveGeo: MutableLiveData<ArrayList<GeoPoint>>,
    private val roadManager: RoadManager,
    private val list: ArrayList<GeoPoint>
) : Thread() {
    override fun run() {
        super.run()
        val road = roadManager.getRoad(list)
        val roadOverly = RoadManager.buildRoadOverlay(road)
        liveGeo.postValue(roadOverly.points)
    }
}