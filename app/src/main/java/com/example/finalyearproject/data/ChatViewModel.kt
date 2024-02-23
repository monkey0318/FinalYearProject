package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.util.*

class ChatViewModel(var user_id: String, var customer_id: String) : ViewModel() {
    val Message = Firebase.firestore.collection(user_id+"_"+customer_id)
    val messages = MutableLiveData<List<Message>>()
    private var name = ""

    init {

        viewModelScope.launch {

            Message.orderBy("date") .addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener
                messages.value = snap.toObjects()
            }
        }
    }
    fun addMessage (message : String){
        val t = Message(
            Text_Message = message,
            Receiver_Id = customer_id,
            Sender_Id = user_id,
            Date = Calendar.getInstance().time
        )
        Message.add(t)
    }
}


class ChatViewModelFactory (var user_id: String, var customer_id: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(user_id,customer_id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}