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
        private val list: List<Sms>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var selectedIds: List<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.list_item_message, parent, false)
        return MessageViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val msg = list[position]

        holder.sender.text = msg.sender
        holder.msgBody.text = msg.body

        if (selectedIds.contains(msg.internalId)) {
            //if item is selected then,set foreground color of FrameLayout.
            holder.rootView.foreground = ColorDrawable(ContextCompat.getColor(context, android.R.color.holo_blue_bright))
        } else {
            //else remove selected item color.
            holder.rootView.foreground = ColorDrawable(ContextCompat.getColor(context, android.R.color.transparent))
        }
    }

    override fun getItemCount() = list.size

    fun getItem(position: Int) = list[position]

    fun setSelectedIds(selectedIds: List<Int>) {
        this.selectedIds = selectedIds
        notifyDataSetChanged()
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sender: TextView = itemView.findViewById(R.id.senderTitleText)
        val msgBody: TextView = itemView.findViewById(R.id.msgBodyText)
        val rootView: ConstraintLayout = itemView.findViewById(R.id.msgItemRootView)
    }
}