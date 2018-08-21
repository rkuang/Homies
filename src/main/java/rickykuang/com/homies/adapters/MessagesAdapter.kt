package rickykuang.com.homies.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import rickykuang.com.homies.R
import rickykuang.com.homies.models.Message
import java.text.DateFormat

class MessagesAdapter(private val myDataset: ArrayList<Message>) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    val VT_SENT = 0
    val VT_RECEIVED = 1

    open class ViewHolder(val v: View) : RecyclerView.ViewHolder(v)

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
            VT_SENT -> SentViewHolder(holder.v).bind(message)
            VT_RECEIVED -> ReceivedViewHolder(holder.v).bind(message)
        }
    }

    override fun getItemCount() = myDataset.size

    class SentViewHolder(v: View) : MessagesAdapter.ViewHolder(v) {
        val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
        val messageView = v.findViewById<TextView>(R.id.message_body)
        val timestampView = v.findViewById<TextView>(R.id.timestamp)

        fun bind(message: Message) {
            messageView.text = message.message
            timestampView.text = simpleDateFormat.format(message.timestamp)
        }
    }

    class ReceivedViewHolder(v: View) : MessagesAdapter.ViewHolder(v) {
        val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
        val messageView = v.findViewById<TextView>(R.id.message_body)
        val senderView = v.findViewById<TextView>(R.id.message_from)
        val timestampView = v.findViewById<TextView>(R.id.timestamp)

        fun bind(message: Message) {
            messageView.text = message.message
            senderView.text = message.sender
            timestampView.text = simpleDateFormat.format(message.timestamp)
        }
    }

}