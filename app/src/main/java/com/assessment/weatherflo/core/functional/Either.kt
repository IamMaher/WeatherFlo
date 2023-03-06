package com.assessment.weatherflo.core.functional

sealed class Either<out L, out R> {
    data class Success<out R>(val data: R) : Either<Nothing, R>()
    data class Error<out L>(val failure: L) : Either<L, Nothing>()

    fun <T> fold(success: (R) -> T, failure: (L) -> T): T {
        return when (this) {
            is Success -> success(this.data)
            is Error -> failure(this.failure)
        }
    }
}