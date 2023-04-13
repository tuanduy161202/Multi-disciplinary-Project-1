package com.example.smarthome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter():RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){
    var chatList:ArrayList<ChatMessage> = ArrayList()

    private val SENT = 0
    private val RECEIVED = 1

    inner class ChatViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val tvMessage:TextView = itemView.findViewById(R.id.tvMessage)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view:View = if (viewType == this.SENT){
            LayoutInflater.from(parent.context).inflate(R.layout.sent_message_box, parent,false)
        } else{
            LayoutInflater.from(parent.context).inflate(R.layout.received_message_box, parent, false)
        }
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = this.chatList[position]
        holder.tvMessage.text = chat.text
    }

    override fun getItemViewType(position: Int): Int {
        return chatList[position].type
    }

    override fun getItemCount(): Int {
        return this.chatList.size
    }
}