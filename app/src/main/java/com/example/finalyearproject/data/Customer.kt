package com.example.finalyearproject.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Customer (
    @DocumentId
    var id               : String = "",
    var email            : String = "",
    var password         : String = "",
    var name             : String = "",
    var photo            : Blob?  = null,
    var user_address     : String = "",
    var user_role        : String = "",
    var date             : Date = Date(),
) {
    override fun toString():String{
        return id
    }
}

val CUSTOMER = Firebase.firestore.collection("users")