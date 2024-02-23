package com.example.finalyearproject.data

import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

data class Voucher(
    @DocumentId
    var Id          : String = "",
    var voucher_name        : String = "",
    var discount_amount     : Double = 0.00,

    )

val VOUCHERS = Firebase.firestore.collection("Voucher")