package rickykuang.com.homies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rickykuang.com.homies.R

class MessagesAdapter(private val myDataset: Array<String>): RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    class ViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_received_message, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessagesAdapter.ViewHolder, position: Int) {
        val textView = holder.v.findViewById<TextView>(R.id.message_body)
        textView.text = myDataset[position]
    }

    override fun getItemCount() = myDataset.size

}