package com.interview.mywallst_assignment.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.interview.mywallst_assignment.R

internal class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(gMap: GoogleMap) {
        val mLatitudeStr = intent.getStringExtra("Latitude")
        val mLongitudeStr = intent.getStringExtra("Longitude")
        val mName = intent.getStringExtra("Name")
        val mRecClass = intent.getStringExtra("RecClass")
        val markerTitle = "$mName \n Class : $mRecClass"
        mMap = gMap ?: return
        val location = LatLng(mLatitudeStr?.toDouble()!!, mLongitudeStr?.toDouble()!!)

        mMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(mName)
                .snippet(mRecClass)
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location))
    }

    override fun onResume() {
        super.onResume()

    }
}