package com.example.finalyearproject.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


@SuppressLint("SimpleDateFormat")
class DateRangePicker(
    var dateRangeType: DateRange,
    isCustomRange: Boolean = false,
    var startDate: LocalDateTime = LocalDateTime.now(),
    var endDate: LocalDateTime = LocalDateTime.now()
) {
    var dateRange: ArrayList<LocalDateTime> = ArrayList()
    lateinit var intervalTerm: String
    private val headerDate: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
    lateinit var dtf: DateTimeFormatter
    lateinit var sfp: SimpleDateFormat

    init {
        if (isCustomRange) {
//            setCustomDateRange()
        } else {
            setDateRange()
        }
    }

    private fun setDateRange() {
        when (dateRangeType) {
            DateRange.TODAY -> {
                startDate = LocalDate.now().atStartOfDay()
                endDate = LocalDate.now().atStartOfDay().plusDays(1)
                dtf = DateTimeFormatter.ofPattern("HH:00")
                sfp = SimpleDateFormat("HH:00")
                intervalTerm = "Hour"
                var currDay = startDate
                while (currDay < endDate) {
                    dateRange.add(currDay)
                    currDay = currDay.plusHours(1)
                }
            }
            DateRange.YESTERDAY -> {
                startDate = LocalDate.now().atStartOfDay().minusDays(1)
                endDate = LocalDate.now().atStartOfDay()
                dtf = DateTimeFormatter.ofPattern("HH:00")
                sfp = SimpleDateFormat("HH:00")
                intervalTerm = "Hour"
                var currDay = startDate
                while (currDay < endDate) {
                    dateRange.add(currDay)
                    currDay = currDay.plusHours(1)
                }
            }
            DateRange.LAST_7_DAYS -> {
                startDate = LocalDate.now().atStartOfDay().minusDays(7)
                endDate = LocalDate.now().atStartOfDay()
                dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                sfp = SimpleDateFormat("dd-MMM-yyyy")
                intervalTerm = "Day"
                var currDay = startDate
                while (currDay < endDate) {
                    dateRange.add(currDay)
                    currDay = currDay.plusDays(1)
                }
            }
            DateRange.LAST_30_DAYS -> {
                startDate = LocalDate.now().atStartOfDay().minusDays(30)
                endDate = LocalDate.now().atStartOfDay()
                dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                sfp = SimpleDateFormat("dd-MMM-yyyy")
                intervalTerm = "Day"
                var currDay = startDate
                while (currDay < endDate) {
                    dateRange.add(currDay)
                    currDay = currDay.plusDays(1)
                }
            }
            DateRange.THIS_MONTH -> {
                startDate = LocalDateTime.of(
                    LocalDate.now().year,
                    LocalDate.now().monthValue,
                    1,
                    0,
                    0
                )
                endDate = startDate.plusMonths(1)
                dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                sfp = SimpleDateFormat("dd-MMM-yyyy")
                intervalTerm = "Day"
                var currDay = startDate
                while (currDay < endDate) {
                    dateRange.add(currDay)
                    currDay = currDay.plusDays(1)
                }
            }
            DateRange.LAST_MONTH -> {
                startDate = LocalDate.now().minusMonths(1).withDayOfMonth(1).atStartOfDay()
                endDate = startDate.plusMonths(1)
                dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy")
                sfp = SimpleDateFormat("dd-MMM-yyyy")
                intervalTerm = "Day"
                var currDay = startDate
                while (currDay < endDate) {
                    dateRange.add(currDay)
                    currDay = currDay.plusDays(1)
                }
            }
            DateRange.LAST_12_MONTH -> {
                startDate = LocalDate.now().minusYears(1).withDayOfMonth(1).atStartOfDay()
                endDate = startDate.plusYears(1)
                dtf = DateTimeFormatter.ofPattern("MMM-yyyy")
                sfp = SimpleDateFormat("MMM-yyyy")
                intervalTerm = "Month"
                var currDay = startDate
                while (currDay < endDate) {
                    dateRange.add(currDay)
                    currDay = currDay.plusMonths(1)
                }
            }
            else -> throw Exception("Should not get here!")
        }
    }

    fun getDateRangeHeader(): String {
        return if (startDate == endDate.minusDays(1)) startDate.format(headerDate)
        else (startDate.format(headerDate) + " ~ " + endDate.minusDays(1).format(headerDate))
    }

}

fun convertLocalDateToDate(date: LocalDateTime): Date {
    return Date.from(date.toInstant(ZoneOffset.UTC))
}

fun getFormattedCurrentTime(): String {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
}

fun getCurrentDateTime(): Date {
    return Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
}

enum class DateRange(private val range: String) {
    TODAY("Today"),
    YESTERDAY("Yesterday"),
    LAST_7_DAYS("Last 7 days"),
    LAST_30_DAYS("Last 30 days"),
    THIS_MONTH("This Month"),
    LAST_MONTH("Last Month"),
    LAST_12_MONTH("Last 12 Months");

    override fun toString(): String {
        return range
    }

}