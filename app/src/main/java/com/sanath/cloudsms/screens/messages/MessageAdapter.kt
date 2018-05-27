package com.sanath.cloudsms.screens.messages

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.sanath.cloudsms.R
import com.sanath.smswrapper.models.Sms


/**
 * Created by sanath on 25/05/18.
 */
class MessageAdapter(
        private val context: Context,
        private var messages: List<Sms>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var selectedItems: List<Sms> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_message, parent, false)
        return MessageViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = messages[position]

        holder.sender.text = msg.sender
        holder.msgBody.text = msg.body

        if (selectedItems.contains(msg)) {
            //if item is selected then,set foreground color of FrameLayout.
            holder.rootView.background = ColorDrawable(ContextCompat.getColor(context, R.color.colorSelectedItem))
        } else {
            //else remove selected item color.
            holder.rootView.background = ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent))
        }
    }

    override fun getItemCount() = messages.size

    fun setSelectedItems(selectedItems: List<Sms>) {
        this.selectedItems = selectedItems
        notifyDataSetChanged()
    }

    fun setData(messages: List<Sms>) {
        this.messages = messages
        this.selectedItems = ArrayList()
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sender: TextView = itemView.findViewById(R.id.senderTitleText)
        val msgBody: TextView = itemView.findViewById(R.id.msgBodyText)
        val rootView: ConstraintLayout = itemView.findViewById(R.id.msgItemRootView)
    }
}