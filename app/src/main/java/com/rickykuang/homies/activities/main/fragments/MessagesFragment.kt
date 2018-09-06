package com.rickykuang.homies.activities.main.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.firestore.ListenerRegistration
import com.rickykuang.homies.Homies
import com.rickykuang.homies.R
import com.rickykuang.homies.activities.main.adapters.MessagesAdapter
import com.rickykuang.homies.models.Message
import com.rickykuang.homies.utils.FirestoreUtil
import java.util.*


class MessagesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessagesAdapter
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var messageListener: ListenerRegistration

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_messages, container, false)

        val nothing_textView = v.findViewById<TextView>(R.id.no_messages)
        nothing_textView.visibility = View.GONE

        val messages = ArrayList<Message>()

        viewAdapter = MessagesAdapter(messages)
        viewManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
        viewManager.stackFromEnd = true

        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
                if (bottom < oldBottom) {
                    recyclerView.postDelayed({
                        recyclerView.smoothScrollToPosition(0)
                    }, 100)
                }
            }
        }

        messageListener = FirestoreUtil.initMessagesListener(messages, viewAdapter, recyclerView)

        v.findViewById<ImageButton>(R.id.send_btn).setOnClickListener {
            sendButtonClickListener(v)
        }

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        messageListener.remove()
    }

    fun sendButtonClickListener(v: View) {
        val edit_message = v.findViewById<EditText>(R.id.edit_message)
        if (edit_message.text.isNotEmpty()) {
            val currentUser = Homies.mAuth.currentUser!!
            val message = Message(currentUser.uid, currentUser.displayName!!, edit_message.text.toString(), null)
            FirestoreUtil.addMessage(message)
            edit_message.text.clear()
        }
    }
}