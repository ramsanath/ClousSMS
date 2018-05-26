package com.sanath.smswrapper.models

import com.sanath.smswrapper.util.Util
import java.util.*

/**
 * Created by sanath on 17/05/18.
 */
class Sms {
    var internalId: Int? = null
    var sender: String? = null
    var body: String? = null
    var subject: String? = null
    var receivedAt: Date? = null
    var messageType: Int? = null
    var threadId: Int? = null
    var isRead: Boolean? = null
    var personId: Int? = null
    //  Only for sent sms
    var sentAt: Date? = null
    var messageStatus: Int? = null

    override fun toString(): String {
        return "internalId: $internalId\n" +
                "sender: $sender\n" +
                "body: $body\n" +
                "subject: $subject\n" +
                "receivedAt: $receivedAt\n" +
                "sentAt: $sentAt\n" +
                "messageType: $messageType\n" +
                "threadId: $threadId\n" +
                "personId: $personId\n" +
                "messageStatus: $messageStatus\n" +
                "isRead: $isRead\n\n"
    }

    override fun hashCode(): Int {
        return (sender +
                body +
                Util.dateToTimestamp(receivedAt!!).toString()
                ).hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Sms

        if (sender != other.sender) return false
        if (body != other.body) return false
        if (subject != other.subject) return false
        if (receivedAt != other.receivedAt) return false
        if (messageType != other.messageType) return false
        if (personId != other.personId) return false
        if (sentAt != other.sentAt) return false

        return true
    }
}