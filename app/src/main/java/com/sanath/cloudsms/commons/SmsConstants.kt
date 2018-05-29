package com.sanath.smswrapper

/**
 * Created by sanath on 17/05/18.
 */
object SmsConstants {

    const val BASE_URI = "content://sms"

    /**
     * The columns in the SMS table
     */
    class Attributes {
        companion object {
            const val INTERNAL_ID = "_id"
            const val DATE = "date"
            const val ADDRESS = "address"
            const val MESSAGE_BODY = "body"
            const val MESSAGE_TYPE = "type"
            const val READ_FLAG = "read"
            const val THREAD_ID = "thread_id"
            const val SUBJECT = "subject"
            const val STATUS = "status"
            const val PERSON_ID = "person"
        }
    }

    class MsgBox {
        companion object {
            const val ALL = ""
            const val INBOX = "inbox"
            const val SENT = "sent"
            const val DRAFT = "draft"
            const val OUTBOX = "outbox"
            const val FAILED = "failed"
            const val QUEUED = "queued"
        }
    }

    /**
     * Integer code that represents the type of SMS
     */
    class MsgTypes {
        companion object {
            const val MESSAGE_TYPE_ALL = 0
            const val MESSAGE_TYPE_INBOX = 1
            const val MESSAGE_TYPE_SENT = 2
            const val MESSAGE_TYPE_DRAFT = 3
            const val MESSAGE_TYPE_OUTBOX = 4
            const val MESSAGE_TYPE_FAILED = 5
            const val MESSAGE_TYPE_QUEUED = 6
        }
    }

    /**
     * The delivery status of the message
     */
    class MsgStatus {
        companion object {
            const val STATUS_COMPLETE = 0
            const val STATUS_NONE = -1
            const val STATUS_PENDING = 32
            const val STATUS_FAILED = 64
        }
    }
}
