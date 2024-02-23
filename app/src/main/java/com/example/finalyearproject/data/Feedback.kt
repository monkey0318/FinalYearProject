package com.example.finalyearproject.data

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class Feedback (
    @DocumentId
    var Feedback_Id : String = "",
    var event_id : String = "",
    var feedback_text : String ="",
    var user_id : String = "",
    )

val FEEDBACKS = Firebase.firestore.collection("Feedback")