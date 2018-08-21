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
import rickykuang.com.homies.R
import rickykuang.com.homies.adapters.MessagesAdapter

const val PAGE = "object"

class MessagesFragment : Fragment() {

    private val TAG = "Messages Fragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_messages, container, false)
        arguments?.takeIf { it.containsKey(PAGE) }?.apply {
            Log.d(TAG, "Argument received")
        }

        val nothing_textView = v.findViewById<TextView>(R.id.no_messages)
        nothing_textView.visibility = View.GONE

        val messages = arrayOf("hello", "world", "Hey hows its going?!", "What's up?", "I love this app!", "Me, too",
                "Hello, world! Welcome to Homies! I am Homey, Homie of the Homies").reversedArray()

        viewAdapter = MessagesAdapter(messages)
        viewManager = LinearLayoutManager(context, 1, true)

        recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return v
    }
}
