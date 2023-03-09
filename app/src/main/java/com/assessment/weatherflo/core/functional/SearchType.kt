package com.assessment.weatherflo.core.functional


sealed class SearchType(val value: String, val type: String) {
    object Zipcode : SearchType("ZipCode", "zip")
    object LatLon : SearchType("latLon", "reverse")
    object Query : SearchType("query", "direct")
}

fun String.searchType(): String {
    val zipCodeRegex = Regex("^\\d{5}(?:[-\\s]\\d{4})?$")
    val latLongRegex = Regex("^[-+]?\\d{1,2}(?:\\.\\d{1,6})?,?\\s*[-+]?\\d{1,3}(?:\\.\\d{1,6})?\$")

    return when {
        this.matches(zipCodeRegex) -> "ZipCode"
        this.matches(latLongRegex) -> "latLon"
        else -> "query"
    }
}

fun String.toCoordinate(): Pair<Double, Double>? {
    val pattern = "^([-+]?\\d{1,2}(?:\\.\\d{1,6})?),?\\s*([-+]?\\d{1,3}(?:\\.\\d{1,6})?)$".toRegex()
    val matchResult = pattern.find(this.trim())
    return matchResult?.let {
        val latitude = it.groupValues[1].toDouble()
        val longitude = it.groupValues[2].toDouble()
        Pair(latitude, longitude)
    }
}