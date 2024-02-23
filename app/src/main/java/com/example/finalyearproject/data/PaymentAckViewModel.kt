package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalyearproject.event_management.EVENTS
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PaymentAckViewModel: ViewModel() {

    private val coll = Firebase.firestore.collection("Payment")
    private var paymentData = listOf<Sales>()
    private val payments = MutableLiveData<List<Sales>>()
    val payment_results = MutableLiveData<List<Sales>>()

    private var p_userId = ""

    init {
        coll.addSnapshotListener { snap, _ -> payments.value = snap?.toObjects() }

        viewModelScope.launch {
            val events = EVENTS.get().await().toObjects<Sales>()

            EVENTS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                paymentData = snap.toObjects<Sales>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (payments.value != null){

            var paymentList = if (p_userId.isNotBlank()) {
                payments.value!!.filter { p -> p.user_Id.contains(p_userId, true)}
            } else {
                payments.value
            }

            payment_results.value = paymentList
        }
    }

    fun get(pid: String): Sales? {
        return payments.value?.find { p -> p.Id == pid }
    }

    fun delete(pid: String) {
        coll.document(pid).delete()
    }

    fun search(p_userId: String) {
        this.p_userId = p_userId
        updateResult()
    }

}