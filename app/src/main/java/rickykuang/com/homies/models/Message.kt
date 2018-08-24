package rickykuang.com.homies.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(var senderId: String,
                   var senderName: String,
                   var message: String,
                   @ServerTimestamp var timestamp: Date?) {

    constructor(): this("", "", "",null)

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        val that = other as Message
        if (senderId.equals(that.senderId) &&
                senderName.equals(that.senderName) &&
                message.equals(that.message)) return true
        return false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}