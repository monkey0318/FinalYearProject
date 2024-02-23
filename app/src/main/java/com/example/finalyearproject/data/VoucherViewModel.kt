package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class VoucherViewModel: ViewModel() {

    private val coll = Firebase.firestore.collection("Voucher")
    private var voucherData = listOf<Voucher>()
    private val vouchers = MutableLiveData<List<Voucher>>()
    val voucher_results = MutableLiveData<List<Voucher>>()

    private var name = ""

    init {
        coll.addSnapshotListener { snap, _ -> vouchers.value = snap?.toObjects() }

        viewModelScope.launch {
            val vouchers = VOUCHERS.get().await().toObjects<Voucher>()

            VOUCHERS.addSnapshotListener { snap, _ ->
                if (snap == null) return@addSnapshotListener

                voucherData = snap.toObjects<Voucher>()

                updateResult()
            }
        }
    }

    private fun updateResult() {
        if (vouchers.value != null){

            var voucherList = if (name.isNotBlank()) {
                vouchers.value!!.filter { v -> v.voucher_name.contains(name, true)}
            } else {
                vouchers.value
            }

            voucher_results.value = voucherList
        }
    }

    fun search(name: String) {
        this.name = name
        updateResult()
    }


    fun get(id: String): Voucher? {
        return vouchers.value?.find { v -> v.Id == id }
    }


    fun delete(id: String) {
        coll.document(id).delete()
    }

    fun set(v: Voucher) {
        coll.document(v.Id).set(v)
    }

    fun add(v: Voucher) {
        coll.add(v)
    }



    fun idExists(id: String): Boolean {

        return vouchers.value?.any { v -> v.Id == id } ?: false
    }

    fun validate(v: Voucher, insert: Boolean = true): String {
        val regexId = Regex("""^[0-9A-Z]{4}$""")
        var e = ""

        e += if (v.discount_amount == 0.00) "Please enter discount amount.\n"
        else ""

        e += if (v.voucher_name == "") "Voucher code is required.\n"
        else if (v.voucher_name.length > 9) "Voucher code must be less than 9 letters.\n"
        else ""

        return e

    }
}