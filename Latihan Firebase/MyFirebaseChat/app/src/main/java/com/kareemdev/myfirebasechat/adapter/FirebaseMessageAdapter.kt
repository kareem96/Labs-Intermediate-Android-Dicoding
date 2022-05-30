package com.kareemdev.myfirebasechat.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.kareemdev.myfirebasechat.databinding.ItemMessageBinding
import com.kareemdev.myfirebasechat.model.Message

class FirebaseMessageAdapter(options:FirebaseRecyclerOptions<Message>, private val currentUserName:String?): FirebaseRecyclerAdapter<Message, FirebaseMessageAdapter.MessageViewHolder>(options) {
    class MessageViewHolder(private val binding: ItemMessageBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int, model: Message) {
        TODO("Not yet implemented")
    }
}