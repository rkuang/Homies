package com.rickykuang.homies.utils

import android.support.v7.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.rickykuang.homies.activities.main.adapters.MessagesAdapter
import com.rickykuang.homies.models.Message
import timber.log.Timber
import java.util.*

object FirestoreUtil {

    val TAG = "FirestoreUtil"
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    fun initMessagesListener(messages: ArrayList<Message>, adapter: MessagesAdapter, recyclerView: RecyclerView): ListenerRegistration {
        return db.collection("messages").orderBy("timestamp", Query.Direction.ASCENDING).limit(50)
                .addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
                    if (e != null) {
                        Timber.e("Listen error: $e")
                        return@EventListener
                    }

                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                Timber.d("Message added: ${dc.document.data}")
                                messages.add(0,
                                        Message(dc.document.get("senderId") as String,
                                                dc.document.get("senderName") as String,
                                                dc.document.get("message") as String,
                                                dc.document.get("timestamp") as Date?)
                                )

                                adapter.notifyItemInserted(0)
                                recyclerView.scrollToPosition(0)

                                if (messages.size > 1 && messages[1].senderId.equals(messages[0].senderId)) {
                                    adapter.notifyItemChanged(1)
                                }
                            }
                            DocumentChange.Type.MODIFIED -> {
                                Timber.d("Timber: Timestamp updated")
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
                            DocumentChange.Type.REMOVED -> Timber.d("Message removed")
                        }
                    }
                })
    }

    fun addMessage(message: Message) {
        db.collection("messages")
                .add(message)
    }
}