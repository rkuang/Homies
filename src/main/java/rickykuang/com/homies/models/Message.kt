package rickykuang.com.homies.models

import java.util.*

data class Message(val sender: String,
                   val message: String,
                   val timestamp: Date?) {

    constructor(): this("", "", null)

}