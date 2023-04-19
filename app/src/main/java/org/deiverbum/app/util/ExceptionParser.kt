package org.deiverbum.app.util

import org.deiverbum.app.R
import retrofit2.HttpException

object ExceptionParser {

    fun getMessage(exception: Exception): Int {
        return when (exception) {
            is HttpException -> getHttpErrorMessage(exception)
            else -> generalError()
        }
    }

    private fun getHttpErrorMessage(exception: HttpException): Int {
        return when (exception.code()) {
            404 -> R.string.error_description
            else -> generalError()
        }
    }

    private fun generalError() = R.string.error_reporting
}