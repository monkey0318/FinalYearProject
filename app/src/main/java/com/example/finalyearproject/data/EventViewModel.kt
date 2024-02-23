package com.example.finalyearproject.data

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalyearproject.event_management.EVENTS
import com.example.finalyearproject.event_management.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.text.ParseException
import java.text.SimpleDateFormat

class EventViewModel : ViewModel() {

    private val coll = Firebase.firestore.collection("event")
    private var eventData = listOf<Event>()
    private val events = MutableLiveData<List<Event>>()
    val event_results = MutableLiveData<List<Event>>()

    private var ev_name = ""

    init {
        coll.addSnapshotListener { snap, _ -> events.value = snap?.toObjects() }

        viewModelScope.launch {
            val events = EVENTS.get().await().toObjects<Event>()

            EVENTS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                eventData = snap.toObjects<Event>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (events.value != null){

            var eventList = if (ev_name.isNotBlank()) {
                events.value!!.filter { ev -> ev.event_name.contains(ev_name, true)}
            } else {
                events.value
            }

            event_results.value = eventList
        }
    }

    fun get(evid: String): Event? {
        return events.value?.find { ev -> ev.event_id == evid }
    }


    fun delete(evid: String) {
        coll.document(evid).delete()
    }


    fun set(ev: Event) {
        coll.document(ev.event_id).set(ev)
    }

    fun evidExists(ev_id: String): Boolean {

        return events.value?.any { ev -> ev.event_id == ev_id } ?: false
    }




    fun validate(ev: Event, insert: Boolean = true): String {
        val regexId = Regex("""^[0-9E]{4}$""")
        var e = ""

        if (insert) {
            e += if (ev.event_id == "") "Id is required.\n"
            else if (!ev.event_id.matches(regexId)) "Id format is invalid.\n"
            else if (evidExists(ev.event_id)) "Id is duplicated.\n"
            else ""
        }

        e += if (ev.event_name == "") "Please enter a name.\n"
        else if (ev.event_name.length < 5) "Name must be more than 5 letters.\n"
        else ""

        e += if (ev.event_date == "") "Please enter date and time.\n"
        else ""


        e += if (ev.event_photo.toBytes().isEmpty()) "Photo is required.\n"
        else ""

        return e
    }

    fun search(ev_name: String) {
        this.ev_name = ev_name
        updateResult()
    }

}