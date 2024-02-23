package com.example.finalyearproject.data

import com.google.firebase.firestore.DocumentId
import java.util.*

data class Message (
    @DocumentId
    var Text_Id:String = "",
    var Sender_Id : String = "",
    var Receiver_Id : String = "",
    var Text_Message : String ="",
    var Date : Date = Date()

)



