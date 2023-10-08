package org.deiverbum.app.util

import org.deiverbum.app.R
import retrofit2.HttpException

object ExceptionParser {

    fun getMessage(exception: Exception): String {
        return exception.message.toString()
        /* if (exception.) {
            /*is HttpException -> getHttpErrorMessage(exception)*/
            return "exception.message"
        }else{
            return generalError()
        }*/
    }

    private fun getHttpErrorMessage(exception: HttpException): String {
        return when (exception.code()) {
            404 -> R.string.error_description.toString()
            else -> "generalError()"
        }
    }

    private fun generalError(): String = R.string.error_reporting.toString()
}