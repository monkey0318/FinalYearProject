package com.example.finalyearproject.data

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    val userLiveData = MutableLiveData<User?>()
    private var listener: ListenerRegistration? = null

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }

    suspend fun login(ctx: Context,email: String,password: String,remember: Boolean = false): Boolean {

        val user = USERS
            .whereEqualTo("user_email",email)
            .whereEqualTo("user_password",password)
            .get()
            .await()
            .toObjects<User>()
            .firstOrNull() ?: return false

        listener?.remove()
        listener = USERS.document(user.user_id).addSnapshotListener { doc, _ ->
            userLiveData.value = doc?.toObject<User>()
        }

        return true
    }

    fun getUser(): User? {
        return userLiveData.value
    }

    fun logout(ctx: Context) {
        listener?.remove()
        userLiveData.value = null
    }
}