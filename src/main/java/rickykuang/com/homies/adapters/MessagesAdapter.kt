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

        var last_message: Message? = null
        var next_message: Message? = null
        var flag = 0
        var bool1 = false
        var bool2 = false

        if (position < myDataset.size-1)
            last_message = myDataset[position+1]

        if (position > 0)
            next_message = myDataset[position-1]

        if (last_message != null)
            bool1 = last_message.senderId.equals(message.senderId)
        if (next_message != null)
            bool2 = next_message.senderId.equals(message.senderId)

//        if (bool1)
//            flag = FLAG_RM_NAME
//        if (bool2)
//            flag = FLAG_RM_AVATAR
//        if (bool1 && bool2)
//            flag = FLAG_RM_BOTH

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
            timestampView.text = simpleDateFormat.format(message.timestamp)
            when (flag) {
                0 -> {}
                else -> {
                    padding.visibility = View.GONE
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
            Log.d(TAG, message.message)
            messageView.text = message.message
            senderView.text = message.senderName
            timestampView.text = simpleDateFormat.format(message.timestamp)
            Log.d(TAG, "Flag is $flag  $message")
            when (flag) {
                FLAG_RM_NAME -> senderView.visibility = View.GONE
                FLAG_RM_AVATAR -> {
                    avatarView.visibility = View.INVISIBLE
                    padding.visibility = View.GONE
                }
                FLAG_RM_BOTH -> {
                    senderView.visibility = View.GONE
                    avatarView.visibility = View.INVISIBLE
                    padding.visibility = View.GONE
                }
            }
        }
    }

}