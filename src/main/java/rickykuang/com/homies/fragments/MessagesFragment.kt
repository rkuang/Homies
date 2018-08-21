package rickykuang.com.homies.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import rickykuang.com.homies.R

const val PAGE = "object"

class MessagesFragment : Fragment() {

    val TAG = "Messages Fragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_messages, container, false)
        arguments?.takeIf { it.containsKey(PAGE) }?.apply {
            Log.d(TAG, "Argument received")
        }
        return v
    }
}
