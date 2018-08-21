package rickykuang.com.homies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rickykuang.com.homies.R
import rickykuang.com.homies.models.Message

class MessagesAdapter(private val myDataset: ArrayList<Message>) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    val VT_SENT = 0
    val VT_RECEIVED = 1

    class ViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    override fun getItemViewType(position: Int): Int {
        val message: Message = myDataset[position]

        if (message.sender.equals("Ricky Kuang"))
            return VT_SENT
        else
            return VT_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesAdapter.ViewHolder {
        lateinit var v : View
        when (viewType) {
            VT_SENT -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_sent_message, parent, false)
            VT_RECEIVED -> v = LayoutInflater.from(parent.context).inflate(R.layout.item_received_message, parent, false)
        }
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MessagesAdapter.ViewHolder, position: Int) {
        val message = myDataset[position]
        when (holder.itemViewType) {
            VT_SENT -> SentViewHolder.bind(message, holder)
            VT_RECEIVED -> ReceivedViewHolder.bind(message, holder)
        }
    }

    override fun getItemCount() = myDataset.size

    object SentViewHolder {
        fun bind(message: Message, holder: MessagesAdapter.ViewHolder) {
            val messageView = holder.v.findViewById<TextView>(R.id.message_body)
            messageView.text = message.text
        }
    }

    object ReceivedViewHolder {
        fun bind(message: Message, holder: MessagesAdapter.ViewHolder) {
            val messageView = holder.v.findViewById<TextView>(R.id.message_body)
            val senderView = holder.v.findViewById<TextView>(R.id.message_from)
            messageView.text = message.text
            senderView.text = message.sender
        }
    }

}