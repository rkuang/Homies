package rickykuang.com.homies.models

import java.util.*

data class Message(val senderId: String,
                   val senderName: String,
                   val message: String,
                   val timestamp: Date?) {

    constructor(): this("", "", "",null)

}