package com.example.finalyearproject.data


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalyearproject.event_management.Event
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SponsorshipViewModel : ViewModel() {

    private val coll = Firebase.firestore.collection("Sponsorship")
    val EVENTS = Firebase.firestore.collection("event")
    private var sponsorData = listOf<Sponsorship>()
    private val sponsors = MutableLiveData<List<Sponsorship>>()
    val sponsor_results = MutableLiveData<List<Sponsorship>>()
    var events = MutableLiveData<List<Event>>()
    var eventId = "0"
    private var ev_id = ""

    init {
        EVENTS.addSnapshotListener { snap, _ ->
            if (snap == null) return@addSnapshotListener
            events.value = snap.toObjects()
        }
    }

    fun setEventid(eventName:String){
        eventId = events.value!!.firstOrNull{
                e -> e.event_name == eventName
        }?.event_name ?: ""

    }


    init {
        coll.addSnapshotListener { snap, _ -> sponsors.value = snap?.toObjects() }

        viewModelScope.launch {
            val sponsors = SPONSORS.get().await().toObjects<Sponsorship>()

            SPONSORS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                sponsorData = snap.toObjects<Sponsorship>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (sponsors.value != null){

            var sponsorList = if (ev_id.isNotBlank()) {
                sponsors.value!!.filter { s -> s.event_Id.contains(ev_id, true)}
            } else {
                sponsors.value
            }

            sponsor_results.value = sponsorList
        }
    }

    fun get(sid: String): Sponsorship? {
        return sponsors.value?.find { s -> s.Id == sid }
    }


    fun delete(sid: String) {
        coll.document(sid).delete()
    }


    fun set(s: Sponsorship) {
        coll.document(s.Id).set(s)
    }

    fun add(s: Sponsorship) {
        coll.add(s)
    }

    fun sidExists(s_id: String): Boolean {

        return sponsors.value?.any { s -> s.Id == ev_id } ?: false
    }




    fun validate(s: Sponsorship, insert: Boolean = true): String {
        val regexId = Regex("""^[0-9E]{4}$""")
        var e = ""




        e += if (s.sponsor_Price.toDouble() == 0.0 ) "Please enter the price.\n"
//        else if (ev.event_name.length < 5) "Name must be more than 5 letters.\n"
        else ""


        e += if (s.sponsor_Logo.toBytes().isEmpty()) "Photo is required.\n"
        else ""

        return e
    }

    fun search(ev_id: String) {
        this.ev_id = ev_id
        updateResult()
    }

}