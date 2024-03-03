package com.example.finalyearproject.user.db

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.finalyearproject.user.db.Users

import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class UsersAuthViewModel : ViewModel() {
    val userLiveData = MutableLiveData<Users?>()
    private var listener: ListenerRegistration? = null

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }

    suspend fun login(ctx: Context, email: String, password: String, remember: Boolean = false): Boolean {

        val user = USER
            .whereEqualTo("user_email",email)
            .whereEqualTo("user_password",password)
            .get()
            .await()
            .toObjects<Users>()
            .firstOrNull() ?: return false

        listener?.remove()
        listener = USER.document(user.user_id).addSnapshotListener { doc, _ ->
            userLiveData.value = doc?.toObject<Users>()
        }

        return true
    }

    fun getUser(): Users? {
        return userLiveData.value
    }

    fun logout(ctx: Context) {
        listener?.remove()
        userLiveData.value = null
    }
}