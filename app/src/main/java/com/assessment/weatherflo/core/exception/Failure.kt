package com.assessment.weatherflo.core.exception

sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()

    /** * Extend this class for feature specific failures.*/
    abstract class FeatureFailure(val message: String?) : Failure()
}