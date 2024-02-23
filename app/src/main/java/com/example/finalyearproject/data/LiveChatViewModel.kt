package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LiveChatViewModel(val user_id:String) : ViewModel() {
    val Customer = Firebase.firestore.collection("users")
    private var customers = listOf<Customer>()
    private val result = MutableLiveData<List<Customer>>()
    private var name = ""

    init {

        viewModelScope.launch {

            Customer.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener
                customers = snap.toObjects()

                updateResult()
            }
        }
    }


    private fun updateResult() {

        var list = customers

        list = list.filter { f ->
            f.name.contains(name, true)
        }

        result.value = list

    }

    fun getResult() = result

    fun search(name: String) {
        this.name = name
        updateResult()
    }

}
class LiveChatViewModelFactory (var id: String) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LiveChatViewModel::class.java)) {
            return LiveChatViewModel(id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}