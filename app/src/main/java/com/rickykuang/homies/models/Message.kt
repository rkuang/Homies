package com.rickykuang.homies.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(var senderId: String = "",
                   var senderName: String = "",
                   var message: String = "",
                   @ServerTimestamp var timestamp: Timestamp? = null) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        val that = other as Message
        if (this.senderId.equals(that.senderId) &&
                this.senderName.equals(that.senderName) &&
                this.message.equals(that.message))
            return true
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}