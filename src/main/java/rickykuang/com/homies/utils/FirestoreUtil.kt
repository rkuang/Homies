package rickykuang.com.homies.utils

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import rickykuang.com.homies.App
import rickykuang.com.homies.adapters.MessagesAdapter
import rickykuang.com.homies.models.Message
import java.util.*

object FirestoreUtil {

    val TAG = "FirestoreUtil"
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun initMessagesListener(messages: ArrayList<Message>, adapter: MessagesAdapter, recyclerView: RecyclerView): ListenerRegistration {
        return db.collection("messages").orderBy("timestamp")
                .addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
                    if (e != null) {
                        Log.e(TAG, "Listen error", e)
                        return@EventListener
                    }
                    // instead of simply using the entire query snapshot
                    // see the actual changes to query results between query snapshots (added, removed, and modified)
                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                Log.d(TAG, "New message: " + dc.document.data)
                                messages.add(0,
                                        Message(dc.document.get("senderId") as String,
                                                dc.document.get("senderName") as String,
                                                dc.document.get("message") as String,
                                                dc.document.get("timestamp") as Date?)
                                )

                                adapter.notifyItemInserted(0)
                                recyclerView.scrollToPosition(0)
//                                try {
//                                    var position = 0
//                                    if (messages[position].senderId.equals(messages[position+1].senderId)) {
//                                        adapter.notifyItemChanged(1)
//                                        position++
//                                    }
//                                } catch (e: IndexOutOfBoundsException) {
//                                    Log.w(TAG, "IndexOutOfBounds")
//                                }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                Log.d(TAG, "Modified message: " + dc.document.data)
                                val m = Message(dc.document.get("senderId") as String,
                                        dc.document.get("senderName") as String,
                                        dc.document.get("message") as String,
                                        dc.document.get("timestamp") as Date?)
                                for (position in 0 until messages.size) {
                                    if (m.equals(messages[position])) {
                                        messages[position].timestamp = m.timestamp
                                        adapter.notifyItemChanged(position)
                                        break
                                    }
                                }
                            }
                            DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed message: " + dc.document.data)
                        }
                    }
                })
    }

    fun addMessage(message: Message) {
        db.collection("messages")
                .add(message)
    }
}