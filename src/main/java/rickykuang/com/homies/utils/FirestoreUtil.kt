package rickykuang.com.homies.utils

import android.util.Log
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import rickykuang.com.homies.adapters.MessagesAdapter
import rickykuang.com.homies.models.Message
import java.util.*

object FirestoreUtil {

    val TAG = "FirestoreUtil"

    fun initMessagesListener(db: FirebaseFirestore, messages: ArrayList<Message>, adapter: MessagesAdapter) {
        db.collection("messages").orderBy("timestamp")
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
                                messages.add(0, Message(dc.document.get("message") as String, dc.document.get("sender") as String))
                                adapter.notifyDataSetChanged()
                            }
                            DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified city: " + dc.document.data)
                            DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed city: " + dc.document.data)
                        }
                    }

        })
    }
}