package com.sanath.smswrapper.sms

import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.sanath.smswrapper.SmsConstants
import com.sanath.smswrapper.SmsConstants.Attributes
import com.sanath.smswrapper.models.Sms
import com.sanath.smswrapper.util.Util

/**
 * Created by sanath on 17/05/18.
 *
 * Fetch the sms using builder pattern
 */
class SmsFetcher private constructor() {

    private lateinit var builder: Builder
    private var limit: Int = -1

    private constructor(builder: Builder) : this() {
        this.builder = builder
        limit = builder.limit!!
    }

    internal fun get(): List<Sms> {
        //TODO("Debug the sort option")
        val sort = if (builder.sortByField == null) null else
            builder.sortByField + " " + (if (builder.sortOrder == null) "ASC" else builder.sortOrder)

        val smsList = ArrayList<Sms>()
        val cursor: Cursor = builder.context?.contentResolver!!.query(
                getUri(),
                arrayOf(
                        Attributes.INTERNAL_ID,
                        Attributes.ADDRESS,
                        Attributes.DATE,
                        Attributes.MESSAGE_BODY,
                        Attributes.MESSAGE_TYPE,
                        Attributes.READ_FLAG,
                        Attributes.THREAD_ID,
                        Attributes.SUBJECT,
                        Attributes.PERSON_ID,
                        Attributes.STATUS),
                null,
                null,
                null)

        //  Just to reduce the number of lines
        fun getValue(column: String) = cursor.getString(cursor.getColumnIndex(column))

        cursor.moveToFirst()
        while (cursor.moveToNext()) {
            if (limit == 0) break

            val sms = Sms()
            sms.internalId = getValue(Attributes.INTERNAL_ID).toInt()
            sms.threadId = getValue(Attributes.THREAD_ID).toInt()
            sms.sender = getValue(Attributes.ADDRESS)
            sms.receivedAt = Util.timestampToDate(getValue(Attributes.DATE))
            sms.body = getValue(Attributes.MESSAGE_BODY)
            sms.messageType = getValue(Attributes.MESSAGE_TYPE)?.toInt()
            sms.isRead = getValue(Attributes.READ_FLAG).toInt() == 1
            sms.subject = getValue(Attributes.SUBJECT)
            sms.personId = getValue(Attributes.PERSON_ID)?.toInt()
            sms.messageStatus = getValue(Attributes.STATUS).toInt()

            if (isMatching(sms)) {
                smsList.add(sms)
                limit -= 1
            }
        }

        cursor.close()

        return smsList
    }

    companion object {
        fun builder(context: Context): Builder {
            return Builder(context)
        }
    }

    private fun isMatching(sms: Sms): Boolean {
        //TODO("not implemented")
        return true
    }

    private fun getUri(): Uri {
        return Uri.parse(SmsConstants.BASE_URI + "/${this.builder.from}")
    }

    class Builder internal constructor(internal val context: Context) {

        internal var from: String? = null
        internal var limit: Int? = null
        internal var sortByField: String? = null
        internal var sortOrder: String? = null

        /**
         * The sms box to fetch from
         * ALL (default), INBOX, SENT, DRAFT, OUTBOX, FAILED, QUEUED
         */
        fun from(from: String): Builder {
            this.from = from
            return this
        }

        /**
         * The number of messages to return
         * Note: If the limit is higher than available the available number of messages is returned as is
         */
        fun limit(limit: Int): Builder {
            this.limit = limit
            return this
        }

        fun sortAscBy(value: String): Builder {
            this.sortByField = value
            this.sortOrder = "ASC"
            return this
        }

        fun sortDescBy(value: String): Builder {
            this.sortByField = value
            this.sortOrder = "DESC"
            return this
        }

        fun get(): List<Sms> {
            if (from == null) {
                this.from = SmsConstants.MsgBox.ALL
            }
            if (limit == null) {
                this.limit = -1
            }
            if (sortByField == null) {
                this.sortByField = ""
                this.sortOrder = ""
            }
            return SmsFetcher(this).get()
        }
    }
}