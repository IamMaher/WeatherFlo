package com.assessment.weatherflo.core.functional

sealed class Units(val value: String) {
    object Metric : Units("Metric")
    object Imperial : Units("Imperial")
}
