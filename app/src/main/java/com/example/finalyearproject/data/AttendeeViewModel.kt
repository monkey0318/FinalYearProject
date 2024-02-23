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

class AttendeeViewModel: ViewModel() {
    private val coll = Firebase.firestore.collection("Payment")
    private var attendeeData = listOf<Sales>()
    private val attendees = MutableLiveData<List<Sales>>()
    val attendee_results = MutableLiveData<List<Sales>>()

    private var attd_event = ""

    init {
        coll.addSnapshotListener { snap, _ -> attendees.value = snap?.toObjects() }

        viewModelScope.launch {
            val events = EVENTS.get().await().toObjects<Sales>()

            EVENTS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                attendeeData = snap.toObjects<Sales>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (attendees.value != null){

            var attendeeList = if (attd_event.isNotBlank()) {
                attendees.value!!.filter { attd -> attd.event_Id.contains(attd_event, true)}
            } else {
                attendees.value
            }

            attendee_results.value = attendeeList
        }
    }

    fun get(attdId: String): Sales? {
        return attendees.value?.find { attd -> attd.event_Id == attdId }
    }


    fun delete(evid: String) {
        coll.document(evid).delete()
    }




    fun search(attd_event: String) {
        this.attd_event = attd_event
        updateResult()
    }

}