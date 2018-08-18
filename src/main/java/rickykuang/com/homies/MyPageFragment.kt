package rickykuang.com.homies

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

const val PAGE = "object"

class MyPageFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.my_page_fragment, container, false)
        arguments?.takeIf { it.containsKey(PAGE) }?.apply {
            val textView: TextView = v.findViewById(R.id.textView)
            textView.text = getInt(PAGE).toString()
        }
        return v
    }
}
