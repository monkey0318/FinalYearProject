package com.example.finalyearproject.user.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class User(
    @DocumentId
    var user_id          : String = "",
    var user_name        : String = "",
    var user_email       : String = "",
    var user_password    : String = "",
    var user_address     : String = "",
    var user_photo       : Blob = Blob.fromBytes(ByteArray(0)),
    var user_role        : String = "" ,
    var date             : Date = Date(),
    var login_fail_count : Int = 0,
) {
    override fun toString():String{
        return user_id
    }
}

val USERS = Firebase.firestore.collection("USERS")