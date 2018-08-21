package rickykuang.com.homies.models

import java.util.*

data class Message(val text: String,
//                   val timestamp: Date,
                   val sender: String) {

    constructor(): this("", "")

}