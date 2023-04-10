package com.example.smarthome

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class IntentAdapter():RecyclerView.Adapter<IntentAdapter.IntentViewHolder>(){
    var IntentList:ArrayList<IntentClass> = ArrayList()
    var onIntentClick:((Int) -> Unit)? = null
    var selected = -1

    inner class IntentViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val intentTextView:TextView = itemView.findViewById(R.id.tvIntentName)
        val pin:ImageView = itemView.findViewById(R.id.pin)

}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_intent, parent, false)
        return IntentViewHolder(view)
    }

    override fun onBindViewHolder(holder: IntentViewHolder, position: Int) {
        val intentItem = this.IntentList[position]

        holder.intentTextView.text = intentItem.intent_name
        holder.itemView.setOnClickListener {
            Log.i("taggg", "set on click listener")
            val pre = selected
            selected = position
            if (pre == selected){
                selected = -1
                onIntentClick?.invoke(-1)
            }
            notifyDataSetChanged()

        }

        if (selected == position){
            holder.pin.visibility = View.VISIBLE
            onIntentClick?.invoke(position)

        }
        else{
            holder.pin.visibility = View.INVISIBLE
        }




    }

    override fun getItemCount(): Int {
        return this.IntentList.size
    }
}