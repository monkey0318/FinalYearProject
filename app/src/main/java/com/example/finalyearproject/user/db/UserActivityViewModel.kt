package com.example.finalyearproject.user.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.google.firebase.firestore.ktx.toObject

class UserActivityViewModel : ViewModel() {
    val userMutableLiveData = MutableLiveData<Users>()

    fun getUser(userID: String) {
        USER.document(userID).get().addOnSuccessListener {
            userMutableLiveData.value = it.toObject()
        }
    }
}