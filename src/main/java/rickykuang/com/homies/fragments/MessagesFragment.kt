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
import com.google.firebase.firestore.FirebaseFirestore
import rickykuang.com.homies.R
import rickykuang.com.homies.adapters.MessagesAdapter
import rickykuang.com.homies.models.Message
import rickykuang.com.homies.utils.FirestoreUtil
import java.util.*

const val PAGE = "object"

class MessagesFragment : Fragment() {
    private val TAG = "MessagesFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessagesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate: ")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "OnCreateView: ")

        val v: View = inflater.inflate(R.layout.fragment_messages, container, false)
        arguments?.takeIf { it.containsKey(PAGE) }?.apply {
            Log.d(TAG, "Argument received")
        }

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

        val db = FirebaseFirestore.getInstance()
        FirestoreUtil.initMessagesListener(db, messages, viewAdapter, recyclerView)

        v.findViewById<ImageButton>(R.id.send_btn).setOnClickListener {
            send_button_click_listener(db, v)
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

    fun send_button_click_listener(db: FirebaseFirestore, v: View) {
        val edit_message = v.findViewById<EditText>(R.id.edit_message)
        if (edit_message.text.isNotEmpty()) {
            val message = Message("Ricky Kuang", edit_message.text.toString(), Date())
            FirestoreUtil.addMessage(db, message)
            edit_message.text.clear()
        }
    }
}