package com.halac123b.smarthome

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAddTask = findViewById<ImageView>(R.id.addTask);
        btnAddTask.setOnClickListener({
            val nextPage = Intent(this, AddCmd::class.java);
            startActivity(nextPage);
        });

//        val deleteBtn = findViewById<ImageView>(R.id.delete);
//        deleteBtn.setOnClickListener({
//            (deleteBtn.getParent().getParent() as ViewGroup).removeView(deleteBtn.getParent() as View);
//        })
    }
    fun onDelete(view: View) {
        (view.getParent().getParent() as ViewGroup).removeView(view.getParent() as View)
    }
}