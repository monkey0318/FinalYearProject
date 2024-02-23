package com.example.finalyearproject.event_management

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Event(
    @DocumentId
    var event_id          : String = "",
    var event_name        : String = "",
    var event_description : String = "",
    var event_sponsorship : String = "",
    var event_status      : String = "",
    var event_website     : String = "",
    var event_location    : GeoPoint = GeoPoint(0.0,0.5),
    var event_phone       : String = "",
    var event_price       : Double = 0.0,
    var event_photo       : Blob = Blob.fromBytes(ByteArray(0)),
    var event_date        : String = "",

    ) {
    override fun toString():String{
        return event_name
    }
}

val EVENTS = Firebase.firestore.collection("event")
