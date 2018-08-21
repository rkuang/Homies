package rickykuang.com.homies.utils

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import rickykuang.com.homies.adapters.MessagesAdapter
import rickykuang.com.homies.models.Message
import java.util.*

object FirestoreUtil {

    val TAG = "FirestoreUtil"

    fun getMessages(mFirestore: FirebaseFirestore, messages: ArrayList<Message>, adapter: MessagesAdapter) {
        val messagesRef = mFirestore.collection("messages")
        messagesRef.get().addOnCompleteListener { task->
            if (task.isSuccessful) {
                for (document in task.result) {
                    messages.add(Message(document.get("message") as String, document.get("sender") as String))
                    Log.d(TAG, document.get("sender").toString())
                }
                adapter.notifyDataSetChanged()
            }
        }
    }
}