package com.example.mymap

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.example.mymap.databinding.ActivityMapsBinding
import com.example.mymap.tasks.GeoCoderTask
import com.example.mymap.tasks.RoutineTask
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.osmdroid.bonuspack.routing.MapQuestRoadManager
import org.osmdroid.util.GeoPoint
import java.util.*

const val LOCATION_REQ_CODE = 101
const val RESOLVABLE_EXACTION_CODE = 102
const val KEY = "dSDJGXbt0XtPgUtr1r7XDydfdo3oIGIq"

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private var hasEnabled = false
    private val locationService by lazy(LazyThreadSafetyMode.NONE) {
        LocationServices.getFusedLocationProviderClient(
            this
        )
    }
    private val locList = arrayListOf<GeoPoint>()
    private var roadManager: MapQuestRoadManager? = null
    private val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            geoCoder(result.lastLocation)
        }
    }

    private fun createPin(loc: Location, address: Address) {
        val myLoc = LatLng(loc.latitude, loc.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(myLoc, 20F)
        mMap.addMarker(MarkerOptions().position(myLoc).title("Men"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLoc))
        mMap.resetMinMaxZoomPreference()
        mMap.animateCamera(cameraUpdate)
        locList.add(GeoPoint(loc.latitude, loc.longitude))
        loadRouting()

    }

    private fun loadRouting() {
        if (locList.size > 1) {
            val data = MutableLiveData<ArrayList<GeoPoint>>()
            val task = RoutineTask(data, roadManager!!, locList)

            data.observe(this) {
                drawPloyLine(it)
            }
            task.start()
        } else {
            Toast.makeText(this, "Lokatsiya uchun ruxsat bering", Toast.LENGTH_LONG).show()
        }
    }

    private fun drawPloyLine(arrayList: ArrayList<GeoPoint>) {
        val option = PolylineOptions()
        arrayList.forEach {
            option.add(LatLng(it.latitude, it.longitude))
        }
        option.color(Color.BLUE)
        mMap.addPolyline(option)
    }

    private fun geoCoder(loc: Location) {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val liveData = MutableLiveData<Address>()

        val geoTask = GeoCoderTask(liveData, geoCoder, loc)
        liveData.observe(this) {
            createPin(loc, it)
        }
        geoTask.start()
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        roadManager = MapQuestRoadManager(KEY)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        findMyLoc()
    }

    private fun findMyLoc() {
        var name = ""
        intent.extras.let {
            if (it == null) return@let else {
                name = it.get("name") as String
            }
        }
        intent.extras.let {
            if (it == null) return@let else {
                val loc = it.get("location") as LatLng
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(loc, 20F)
                mMap.addMarker(MarkerOptions().position(loc).title(name))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc))
                mMap.animateCamera(cameraUpdate)
                locList.add(GeoPoint(loc.latitude, loc.longitude))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!hasEnabled) {
            hasEnabled = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissions(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        LOCATION_REQ_CODE
                    )
                } else {
                    checkLocationAvailability()
                }
            } else {
                checkLocationAvailability()
            }
        } else {
            checkLocationAvailability()
        }
    }

    @SuppressLint("MissingPermission")
    private fun checkLocationAvailability() {
        locationService.locationAvailability
            .addOnSuccessListener {
                if (it.isLocationAvailable) {
                    requestLocation()
                } else {
                    setUpLocationRequest()
                }
            }
            .addOnFailureListener { setUpLocationRequest() }
    }

    private fun setUpLocationRequest() {
        val req = createLocationReq()
        val settingsRequest = LocationSettingsRequest.Builder()
            .addLocationRequest(req)
            .build()

        LocationServices.getSettingsClient(this)
            .checkLocationSettings(settingsRequest)
            .addOnSuccessListener {
                requestLocation()
            }
            .addOnFailureListener { resolvableRequestException(it) }
    }

    private fun createLocationReq() = LocationRequest().apply {
        interval = 10_000
        fastestInterval = 5_000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun resolvableRequestException(it: Exception) {
        if (it is ResolvableApiException) {
            try {
                it.startResolutionForResult(this, RESOLVABLE_EXACTION_CODE)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        locationService.requestLocationUpdates(createLocationReq(), callback, Looper.myLooper()!!)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQ_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            checkLocationAvailability()
        } else {
            Toast.makeText(this, "Lokatsiya", Toast.LENGTH_LONG).show()
        }
    }
}