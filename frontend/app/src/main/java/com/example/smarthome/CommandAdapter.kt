package com.example.smarthome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommandAdapter(var CommandList:ArrayList<Command>):RecyclerView.Adapter<CommandAdapter.CommandViewHolder>(){

    var onItemClick:((Int) -> Unit)? = null
    var onTrashClick:((Int) -> Unit)? = null

    inner class CommandViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val commandTextView:TextView= itemView.findViewById(R.id.tvCommandName)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommandViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item, parent, false)
        return CommandViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommandViewHolder, position: Int) {
        val command = this.CommandList[position]
        if (command.text.length <= 18){
            holder.commandTextView.text = command.text
        }
        else{
            holder.commandTextView.text = command.text.substring(0, 19).plus(" ...")
        }


        holder.itemView.setOnClickListener {
            onItemClick?.invoke(holder.adapterPosition)
        }

        holder.itemView.findViewById<ImageButton>(R.id.trashButton).setOnClickListener {
            onTrashClick?.invoke(holder.adapterPosition)
//            this.CommandList.removeAt(holder.adapterPosition)
//            notifyItemRemoved(holder.adapterPosition)

        }

    }

//    fun setCommandList(data:ArrayList<Command>){
//        this.CommandList = data
//    }

    override fun getItemCount(): Int {
        return this.CommandList.size
    }
}