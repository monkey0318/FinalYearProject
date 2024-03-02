package com.example.finalyearproject.user.db

import android.content.Context
import android.graphics.BitmapFactory
import com.example.finalyearproject.R
import com.example.finalyearproject.util.toBlob
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.Date

data class User(
    @DocumentId
    var user_id: String = "",
    var user_name: String = "",
    var user_email: String = "",
    var user_password: String = "",
    var user_address: String = "",
    var user_photo: Blob = Blob.fromBytes(ByteArray(0)),
    var user_role: String = "",
    var date: Date = Date(),
    var login_fail_count: Int = 0,
) {
    override fun toString():String{
        return user_id
    }
}

val USERS = Firebase.firestore.collection("USERS")

fun restore_user(ctx: Context) {
    USERS.get().addOnSuccessListener { snap->
        for(doc in snap.documents) {
            USERS.document(doc.id).delete()
        }
        val user1 = User(
            user_id = "A123",
            user_name =  "Jenn",
            user_email =  "jenn@gmail.com",
            user_password =  "123",
            user_address =  "Ampang Park",
            user_photo = BitmapFactory.decodeResource(ctx.resources, R.drawable.iu_2).toBlob(),
            user_role =  "USER",
            login_fail_count = 0,
        )
        USERS.document("A123").set(user1)
    }
}