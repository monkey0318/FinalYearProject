package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FeedbackViewModel : ViewModel() {

    private val coll = Firebase.firestore.collection("Feedback")
    private var feedbackData = listOf<Feedback>()
    private val feedbacks = MutableLiveData<List<Feedback>>()
    val results = MutableLiveData<List<Feedback>>()

    private var name = ""

    init {
        coll.addSnapshotListener { snap, _ -> feedbacks.value = snap?.toObjects() }

        viewModelScope.launch {
            val feedbacks = FEEDBACKS.get().await().toObjects<Feedback>()

            FEEDBACKS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                feedbackData = snap.toObjects<Feedback>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (feedbacks.value != null){

            var feedbackList = if (name.isNotBlank()) {
                feedbacks.value!!.filter { f -> f.event_id.contains(name, true)}
            } else {
                feedbacks.value
            }

            results.value = feedbackList
        }
    }

    fun get(id: String): Feedback? {
        return feedbacks.value?.find { f -> f.Feedback_Id == id }
    }


    fun delete(id: String) {
        coll.document(id).delete()
    }


    fun set(f: Feedback) {
        coll.document(f.Feedback_Id).set(f)
    }



    fun search(name: String) {
        this.name = name
        updateResult()
    }

}