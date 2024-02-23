package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.toObject

class ActivityViewModel() : ViewModel() {
    val userMutableLiveData = MutableLiveData<User>()

    fun getUser(userID: String) {
        USERS.document(userID).get().addOnSuccessListener {
            userMutableLiveData.value = it.toObject()
        }
    }

}