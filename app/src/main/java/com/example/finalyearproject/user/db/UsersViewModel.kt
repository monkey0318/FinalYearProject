package com.example.finalyearproject.user.db

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalyearproject.user.db.Users
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel : ViewModel() {

    private val coll = Firebase.firestore.collection("USERS")
    private var userData = listOf<Users>()
    private val users = MutableLiveData<List<Users>>()
    val results = MutableLiveData<List<Users>?>()

    private var name = ""

    init {
        coll.addSnapshotListener { snap, _ -> users.value = snap?.toObjects() }

        viewModelScope.launch {
            val users = com.example.finalyearproject.data.USERS.get().await().toObjects<Users>()

            com.example.finalyearproject.data.USERS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                userData = snap.toObjects<Users>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (users.value != null){

            var userList = if (name.isNotBlank()) {
                users.value!!.filter { u -> u.user_name.contains(name, true)}
            } else {
                users.value
            }

            results.value = userList
        }
    }

    fun get(id: String): Users? {
        return users.value?.find { u -> u.user_id == id }
    }


    fun delete(id: String) {
        coll.document(id).delete()
    }


    fun set(u: Users) {
        coll.document(u.user_id).set(u)
    }

    fun idExists(id: String): Boolean {

        return users.value?.any { u -> u.user_id == id } ?: false
    }

    fun emailExists(email: String): Boolean {

        return users.value?.any { u -> u.user_email == email } ?: false
    }

    fun validate(u: Users, insert: Boolean = true): String {
        val regexId = Regex("""^[0-9S]{4}$""")
        var e = ""

        if (insert) {
            e += if (u.user_id == "") "Id is required.\n"
            else if (!u.user_id.matches(regexId)) "Id format is invalid.\n"
            else if (idExists(u.user_id)) "Id is duplicated.\n"
            else ""
        }

        e += if (u.user_name == "") "Please enter a name.\n"
        else if (u.user_name.length < 5) "Name must be more than 5 letters.\n"
        else if (emailExists(u.user_name)) "Name is duplicated.\n"
        else ""



        e += if (u.user_photo.toBytes().isEmpty()) "Photo is required.\n"
        else ""

        return e
    }

    fun search(name: String) {
        this.name = name
        updateResult()
    }
}