package com.example.finalyearproject.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Sales (
    @DocumentId
    var Id            : String = "",
    var event_Id      : String = "",
    var payment_Price : Double = 0.00,
    var total_Ticket  : Int = 0,
    var used_tickets  : Int = 0,
    var payment_Date  : Date = Date(),
    var user_Id       : String = "",
)

val PAYMENT = Firebase.firestore.collection("Payment")