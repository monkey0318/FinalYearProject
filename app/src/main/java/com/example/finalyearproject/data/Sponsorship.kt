package com.example.finalyearproject.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Sponsorship (

        @DocumentId
        var Id : String = "",
        var event_Id : String = "",
        var sponsor_Date : Date = Date(),
        var sponsor_Logo : Blob = Blob.fromBytes(ByteArray(0)),
        var sponsor_Price : Double = 0.0,
        var user_Id : String = "",

        )

val SPONSORS = Firebase.firestore.collection("Sponsorship")

