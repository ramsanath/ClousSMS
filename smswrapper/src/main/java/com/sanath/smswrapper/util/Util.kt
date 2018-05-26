package com.sanath.smswrapper.util

import java.util.*

/**
 * Created by sanath on 17/05/18.
 */
internal class Util {

    companion object {
        fun timestampToDate(timestamp: String) = Date(timestamp.toLong())
        fun dateToTimestamp(date: Date) = date.time / 1000
        fun isAlphaNumeric(s: String) = s.matches(Regex("[a-zA-Z0-9]+"))
        fun isNumericPhoneNumber(number: String) = number.matches(Regex("(\\+\\d{1,2}\\-?)?\\d{3,10}"))
    }
}