package rickykuang.com.homies.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import rickykuang.com.homies.R
import rickykuang.com.homies.adapters.MessagesAdapter
import rickykuang.com.homies.models.Message
import rickykuang.com.homies.utils.FirestoreUtil

const val PAGE = "object"

class MessagesFragment : Fragment() {

    private val TAG = "Messages Fragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: MessagesAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
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
        FirestoreUtil.getMessages(db, messages, viewAdapter)

        return v
    }
}
