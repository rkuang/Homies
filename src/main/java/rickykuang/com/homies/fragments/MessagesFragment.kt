package rickykuang.com.homies.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ListenerRegistration
import rickykuang.com.homies.App
import rickykuang.com.homies.R
import rickykuang.com.homies.adapters.MessagesAdapter
import rickykuang.com.homies.models.Message
import rickykuang.com.homies.utils.FirestoreUtil
import java.sql.Timestamp
import java.util.*


class MessagesFragment : Fragment() {
    private val TAG = "MessagesFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessagesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var messageListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate: ")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "OnCreateView: ")

        val v: View = inflater.inflate(R.layout.fragment_messages, container, false)

        val nothing_textView = v.findViewById<TextView>(R.id.no_messages)
        nothing_textView.visibility = View.GONE

        val messages = ArrayList<Message>()

        viewAdapter = MessagesAdapter(messages)
        viewManager = LinearLayoutManager(context, 1, true)

        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        messageListener = FirestoreUtil.initMessagesListener(messages, viewAdapter, recyclerView)

        v.findViewById<ImageButton>(R.id.send_btn).setOnClickListener {
            send_button_click_listener(v)
        }

        return v
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "OnPause: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume: ")
    }

    fun send_button_click_listener(v: View) {
        val edit_message = v.findViewById<EditText>(R.id.edit_message)
        if (edit_message.text.isNotEmpty()) {
            val currentUser = App.mAuth.currentUser!!
            val message = Message(currentUser.uid, currentUser.displayName!!, edit_message.text.toString(), null)
            FirestoreUtil.addMessage(message)
            edit_message.text.clear()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "OnDestroyView: ")
        messageListener.remove()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy: ")
    }
}