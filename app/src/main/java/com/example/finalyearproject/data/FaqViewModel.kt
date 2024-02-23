package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FaqViewModel: ViewModel() {

    private val coll = Firebase.firestore.collection("FAQ")
    private var faqData = listOf<Faq>()
    private val faqs = MutableLiveData<List<Faq>>()
    val faq_results = MutableLiveData<List<Faq>>()

    private var question = ""

    init {
        coll.addSnapshotListener { snap, _ -> faqs.value = snap?.toObjects() }

        viewModelScope.launch {
            val faqs = FAQS.get().await().toObjects<Faq>()

            USERS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                faqData = snap.toObjects<Faq>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (faqs.value != null){

            var faqList = if (question.isNotBlank()) {
                faqs.value!!.filter { q -> q.Question.contains(question, true)}
            } else {
                faqs.value
            }

            faq_results.value = faqList
        }
    }

    fun get(id: String): Faq? {
        return faqs.value?.find { q -> q.Question_Id == id }
    }


    fun delete(id: String) {
        coll.document(id).delete()
    }


    fun set(q: Faq) {
        coll.document(q.Question_Id).set(q)
    }

    fun idExists(id: String): Boolean {

        return faqs.value?.any { q -> q.Question_Id == id } ?: false
    }



    fun validate(q: Faq, insert: Boolean = true): String {
        var e = ""


        e += if (q.Question == "") "Please enter a Question.\n"
        else if (q.Answer == "") "Please enter an Answer.\n"
        else ""


        return e
    }

    fun search(question: String) {
        this.question = question
        updateResult()
    }

}