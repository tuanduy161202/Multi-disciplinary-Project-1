package com.example.smarthome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class CommandList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.command_list)
        val btnAddTask = findViewById<ImageView>(R.id.addTask)
        btnAddTask.setOnClickListener{
            val nextPage = Intent(this, AddCmd::class.java)
            startActivity(nextPage)
        }

//        val deleteBtn = findViewById<ImageView>(R.id.delete);
//        deleteBtn.setOnClickListener({
//            (deleteBtn.getParent().getParent() as ViewGroup).removeView(deleteBtn.getParent() as View);
//        })
    }
    fun onDelete(view: View) {
        (view.getParent().getParent() as ViewGroup).removeView(view.getParent() as View)
    }
}