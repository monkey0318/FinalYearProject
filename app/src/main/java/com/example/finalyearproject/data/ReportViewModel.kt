package com.example.finalyearproject.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalyearproject.event_management.EVENTS
import com.example.finalyearproject.event_management.Event
import com.example.finalyearproject.util.DateRange
import com.example.finalyearproject.util.DateRangePicker
import com.example.finalyearproject.util.convertLocalDateToDate
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList


class ReportViewModel: ViewModel() {

    private val coll = Firebase.firestore.collection("Payment")

    val sales = MutableLiveData<List<Sales>>()
    var dateRange = MutableLiveData<DateRange>()
    var dateRangePicker = MutableLiveData<DateRangePicker>()
    val sales_results = MutableLiveData<List<Sales>>()
    var retrievedOrderItems = MutableLiveData<Boolean>()

    private val db = Firebase.firestore

    init {
        dateRange.value = DateRange.TODAY
        dateRangePicker.value = DateRangePicker(DateRange.TODAY)
       coll.get().addOnSuccessListener {
            sales.value = it.toObjects()

        }
    }

    private fun getSalesOrder() {
        sales_results.value = sales.value?.filter { s -> s.payment_Date.before(getEndDate()) && s.payment_Date.after(getStartDate()) }

    }



    fun setDateRange(
        dateRange: DateRange,
        isCustomRange: Boolean = false,
        startDate: LocalDateTime = LocalDateTime.now(),
        endDate: LocalDateTime = LocalDateTime.now()
    ) {
        this.dateRange.value = dateRange
        if (isCustomRange) {
            dateRangePicker.value = DateRangePicker(dateRange, isCustomRange,startDate,endDate.plusDays(1))
        } else {
            dateRangePicker.value = DateRangePicker(dateRange)
        }
        getSalesOrder()
    }

    fun getStartDate(): Date {
        return convertLocalDateToDate(dateRangePicker.value!!.startDate)
    }

    fun getEndDate(): Date {
        return convertLocalDateToDate(dateRangePicker.value!!.endDate)
    }

    fun getDateRange(): List<LocalDateTime> {
        return dateRangePicker.value!!.dateRange
    }

    fun formatLocalDateTime(date: LocalDateTime): String {
        return date.format(dateRangePicker.value!!.dtf)
    }

    fun formatDate(date: Date): String {
        return dateRangePicker.value!!.sfp.format(date)
    }

    fun getHeaderDateRange(reportName: String): String {
        return reportName + " " + dateRangePicker.value!!.getDateRangeHeader()
    }

    fun getIntervalTerm(): String {
        return dateRangePicker.value!!.intervalTerm
    }


}