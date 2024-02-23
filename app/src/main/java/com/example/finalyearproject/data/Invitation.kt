package com.example.finalyearproject.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Invitation(
    @DocumentId
    var Invitation_Id : String = "",
    var event_id : String = "",
    var user_id : String = "",
)

val INVITATION = Firebase.firestore.collection("Invitation")