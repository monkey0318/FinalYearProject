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

class InvitationViewModel: ViewModel(){

    private val coll = Firebase.firestore.collection("Invitation")
    val EVENTS = Firebase.firestore.collection("event")
    val CUSTS = Firebase.firestore.collection("users")
    private var invitationsData = listOf<Invitation>()
    private val invitations = MutableLiveData<List<Invitation>>()
    val invitation_results = MutableLiveData<List<Invitation>>()
    var events = MutableLiveData<List<Event>>()
    var customers = MutableLiveData<List<Customer>>()
    var eventId = "0"
    var customerId = "0"
    private var cust_id = ""


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
        CUSTS.addSnapshotListener { snap, _ ->
            if (snap == null) return@addSnapshotListener
            customers.value = snap.toObjects()
        }
    }

    fun setCustid(custID:String){
        customerId = customers.value!!.firstOrNull{
                c -> c.id == custID
        }?.id ?: ""
    }

    init {
        coll.addSnapshotListener { snap, _ -> invitations.value = snap?.toObjects() }

        viewModelScope.launch {
            val invitations = INVITATION.get().await().toObjects<Invitation>()

            INVITATION.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                invitationsData = snap.toObjects<Invitation>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (invitations.value != null){

            var invitationList = if (cust_id.isNotBlank()) {
                invitations.value!!.filter { i -> i.user_id .contains(cust_id, true)}
            } else {
                invitations.value
            }

            invitation_results.value = invitationList
        }
    }

    fun get(ivid: String): Invitation? {
        return invitations.value?.find { i -> i.Invitation_Id == ivid }
    }


    fun delete(ivid: String) {
        coll.document(ivid).delete()
    }


    fun set(i: Invitation) {
        coll.document(i.Invitation_Id).set(i)
    }

    fun add(i: Invitation) {
        coll.add(i)
    }

    fun search(name: String) {
        this.cust_id = name
        updateResult()
    }


}