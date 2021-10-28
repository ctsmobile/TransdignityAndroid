package com.cts.removalspecialist.fcm

import android.util.Log
import com.cts.removalspecialist.models.LatLongModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class FirebaseHelper constructor(userId: String) {

    companion object {
        private const val REMOVAL_SPECIALIST = "removal_specialist"
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