package com.example.finalyearproject.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Faq (
    @DocumentId
    var Question_Id : String = "",
    var Question    : String = "",
    var Answer      : String = "",

)

val FAQS = Firebase.firestore.collection("FAQ")
