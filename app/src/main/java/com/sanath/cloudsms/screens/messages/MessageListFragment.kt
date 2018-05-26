package com.sanath.cloudsms.screens.messages

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanath.cloudsms.R
import com.sanath.cloudsms.commons.Helper
import com.sanath.smswrapper.models.Sms
import java.util.*

/**
 * Created by sanath on 26/05/18.
 */
class MessageListFragment : Fragment() {

    private var messages: List<Sms> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) this.messages = Helper.getMessageArgs(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.fragment_message_list, container, false)!!

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = com.sanath.cloudsms.screens.messages.MessageAdapter(activity, messages)

        return view
    }
}
