package com.transdignity.driver.utilities

import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

//TODO: This code is Used for update lat and long on FireBase
class FirebaseHelper constructor(userId: String) {

    companion object {
        private const val REMOVAL_SPECIALIST = "cab_driver"
    }

    private val onlineDriverDatabaseReference: DatabaseReference = FirebaseDatabase
            .getInstance()
            .reference
            .child(REMOVAL_SPECIALIST)
            .child(userId)

    init {
        onlineDriverDatabaseReference
                .onDisconnect()
                //.removeValue()
    }

    fun updateDriver(latlong: LatLongModel) {
        onlineDriverDatabaseReference
                .setValue(latlong)
        Log.e("LatLongModel Info", " Updated")
    }

    fun deleteDriver() {
        onlineDriverDatabaseReference
                .removeValue()
    }
}