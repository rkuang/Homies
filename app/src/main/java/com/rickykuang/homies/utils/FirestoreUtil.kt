package com.rickykuang.homies.utils

import android.support.v7.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.rickykuang.homies.activities.main.adapters.MessagesAdapter
import com.rickykuang.homies.models.Message
import timber.log.Timber
import java.util.*

object FirestoreUtil {
    private val db: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val messageQuery =  db.collection("messages").orderBy("timestamp", Query.Direction.DESCENDING).limit(50);

    fun addMessage(message: Message) {
        db.collection("messages")
                .add(message)
    }
}