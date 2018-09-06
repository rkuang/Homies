package com.rickykuang.homies.utils

import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.rickykuang.homies.models.Message
import timber.log.Timber


object FirestoreUtil {

    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }
    private val messageRef = db.collection("messages").orderBy("timestamp", Query.Direction.ASCENDING)

    fun initMessagesListener(callback: MessagesListViewCallback): ListenerRegistration {
        return messageRef
                .addSnapshotListener(EventListener<QuerySnapshot> { snapshots, e ->
                    if (e != null) {
                        Timber.e("Listen error $e")
                        return@EventListener
                    }

                    for (dc in snapshots!!.documentChanges) {
                        when (dc.type) {
                            DocumentChange.Type.ADDED -> {
                                Timber.d("Message added: ${dc.document.data}")

                                callback.add(dc.document.toObject(Message::class.java))
                            }
                            DocumentChange.Type.MODIFIED -> {
                                Timber.d("Timestamp updated: ${dc.document.data})")
                                val message = dc.document.toObject(Message::class.java)

                                callback.update(message)
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

    interface MessagesListViewCallback {

        fun add(message: Message)

        fun update(message: Message)

    }
}