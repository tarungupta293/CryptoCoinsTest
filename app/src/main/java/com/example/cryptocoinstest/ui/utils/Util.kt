package com.example.cryptocoinstest.ui.utils

import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Tarun Gupta on 2025-03-09
 *
 * Common singleton class for application utilities methods.
 * Here, I am converting date to different date format and returning the formatted date.
 *
 */
object Util {

    fun changeDateFormat(dateString: String, dateFormat: String): String {
        val fmt = SimpleDateFormat(dateFormat)
        val date: Date = fmt.parse(dateString)

        val fmtOut = SimpleDateFormat("dd MMM yyyy")
        return fmtOut.format(date)
    }
}