package com.assessment.weatherflo.core.extenstion

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDateTime(format: String, zoneId: Int? = null): String {
    val date = Date().apply {
        time = this@toDateTime
    }

    val language = androidx.compose.ui.text.intl.Locale.current.language
    val locale = Locale(language)

    return SimpleDateFormat(format, locale).apply {
        timeZone = TimeZone.getDefault().apply { zoneId?.let { rawOffset = it } }
    }.format(date)
}