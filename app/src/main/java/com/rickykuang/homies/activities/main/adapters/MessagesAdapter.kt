package com.rickykuang.homies.activities.main.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.rickykuang.homies.Homies
import com.rickykuang.homies.R
import com.rickykuang.homies.models.Message
import java.text.DateFormat

class MessagesAdapter(options: FirestoreRecyclerOptions<Message>) : FirestoreRecyclerAdapter<Message, MessagesAdapter.ViewHolder>(options) {

    private val VT_SENT = 0
    private val VT_RECEIVED = 1

    override fun getItemViewType(position: Int): Int {
        val message: Message = getItem(position)

        if (message.senderId.equals(Homies.mAuth.currentUser?.uid)) return VT_SENT
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

    override fun onBindViewHolder(holder: MessagesAdapter.ViewHolder, position: Int, model: Message) {
        when (holder.itemViewType) {
            VT_SENT -> SentViewHolder(holder.v).bind(model)
            VT_RECEIVED -> {
                ReceivedViewHolder(holder.v).bind(model)
            }
        }
    }

    open class ViewHolder(val v: View) : RecyclerView.ViewHolder(v) {
        val simpleDateFormat = DateFormat.getTimeInstance(DateFormat.SHORT)
        val messageView = v.findViewById<TextView>(R.id.message_body)
        val timestampView = v.findViewById<TextView>(R.id.timestamp)

        val senderView = v.findViewById<TextView>(R.id.message_from)
//        val avatarView = v.findViewById<ImageView>(R.id.avatar)

        open fun bind(message: Message) {
            messageView.text = message.message
            if (message.timestamp == null)
                timestampView.text = ""
            else
                timestampView.text = simpleDateFormat.format(message.timestamp)
        }
    }

    class SentViewHolder(v: View) : MessagesAdapter.ViewHolder(v)

    class ReceivedViewHolder(v: View) : MessagesAdapter.ViewHolder(v) {
        override fun bind(message: Message) {
            messageView.text = message.message
            senderView.text = message.senderName
            super.bind(message)
        }
    }
}