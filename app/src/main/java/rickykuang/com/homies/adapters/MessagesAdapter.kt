package rickykuang.com.homies.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import rickykuang.com.homies.App
import rickykuang.com.homies.R
import rickykuang.com.homies.models.Message
import java.text.DateFormat

private val TAG = "MessagesAdapter"
private val FLAG_RM_NAME = 1
private val FLAG_RM_AVATAR = 2
private val FLAG_RM_BOTH = 3

class MessagesAdapter(private val myDataset: ArrayList<Message>) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    private val VT_SENT = 0
    private val VT_RECEIVED = 1

    override fun getItemViewType(position: Int): Int {
        val message: Message = myDataset[position]

        if (message.senderId.equals(App.mAuth.currentUser?.uid)) return VT_SENT
        else return VT_RECEIVED
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

        var top_message: Message? = null
        var bottom_message: Message? = null
        var flag = 0
        var top_same = false
        var bot_same = false

        if (position < myDataset.size-1)
            top_message = myDataset[position+1]

        if (position > 0)
            bottom_message = myDataset[position-1]

        if (top_message != null)
            top_same = top_message.senderId.equals(message.senderId)
        if (bottom_message != null)
            bot_same = bottom_message.senderId.equals(message.senderId)

        if (top_same)
            flag = FLAG_RM_NAME
        else if (bot_same)
            flag = FLAG_RM_AVATAR
        if (top_same && bot_same)
            flag = FLAG_RM_BOTH

        when (holder.itemViewType) {
            VT_SENT -> SentViewHolder(holder.v).bind(message, flag)
            VT_RECEIVED -> {
                ReceivedViewHolder(holder.v).bind(message, flag)
            }
        }
    }

    override fun getItemCount() = myDataset.size

    open class ViewHolder(val v: View) : RecyclerView.ViewHolder(v)

    class SentViewHolder(v: View) : MessagesAdapter.ViewHolder(v) {
        val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
        val messageView = v.findViewById<TextView>(R.id.message_body)
        val timestampView = v.findViewById<TextView>(R.id.timestamp)
        val padding = v.findViewById<View>(R.id.padding)

        fun bind(message: Message, flag: Int) {
            messageView.text = message.message
            if (message.timestamp == null)
                timestampView.text = ""
            else
                timestampView.text = simpleDateFormat.format(message.timestamp)
            when (flag) {
                FLAG_RM_AVATAR -> {
                    padding.visibility = View.GONE
                }
                FLAG_RM_BOTH -> {
                    padding.visibility = View.GONE
                }
                else -> {
                    padding.visibility = View.VISIBLE
                }
            }
        }
    }

    class ReceivedViewHolder(v: View) : MessagesAdapter.ViewHolder(v) {
        val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
        val messageView = v.findViewById<TextView>(R.id.message_body)
        val senderView = v.findViewById<TextView>(R.id.message_from)
        val timestampView = v.findViewById<TextView>(R.id.timestamp)
        val avatarView = v.findViewById<ImageView>(R.id.avatar)
        val padding = v.findViewById<View>(R.id.padding)

        fun bind(message: Message, flag: Int) {
            messageView.text = message.message
            senderView.text = message.senderName
            if (message.timestamp == null)
                timestampView.text = ""
            else
                timestampView.text = simpleDateFormat.format(message.timestamp)
            when (flag) {
                FLAG_RM_NAME -> {
                    senderView.visibility = View.GONE
                    avatarView.visibility = View.VISIBLE
                    padding.visibility = View.VISIBLE
                }
                FLAG_RM_AVATAR -> {
                    senderView.visibility = View.VISIBLE
                    avatarView.visibility = View.INVISIBLE
                    padding.visibility = View.GONE
                }
                FLAG_RM_BOTH -> {
                    senderView.visibility = View.GONE
                    avatarView.visibility = View.INVISIBLE
                    padding.visibility = View.GONE
                }
                else -> {
                    senderView.visibility = View.VISIBLE
                    avatarView.visibility = View.VISIBLE
                    padding.visibility = View.VISIBLE
                }
            }
        }
    }

}