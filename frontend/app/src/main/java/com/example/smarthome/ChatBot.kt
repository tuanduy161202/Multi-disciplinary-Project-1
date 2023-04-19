package com.example.smarthome

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatBot : AppCompatActivity() {

    private lateinit var btnBack:ImageButton
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var sendButton: ImageButton
    private val viewModel: SharedViewModel by lazy{
        ViewModelProvider(this)[SharedViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

        btnBack = findViewById(R.id.backFromChat)
        recyclerView = findViewById(R.id.chatRecycler)
        editTextMessage = findViewById(R.id.editTextUserMessage)
        sendButton = findViewById(R.id.sendButton)


        btnBack.setOnClickListener {
            onBackPressed()
        }


        chatAdapter = ChatAdapter()
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = chatAdapter



//        if (data["type"].toString() == "\"bot_message\""){
//            val chatMessage = ChatMessage(1, data["message"].toString())
//            chatAdapter.chatList.add(chatMessage)
//            chatAdapter.notifyItemInserted(chatAdapter.chatList.size)
//            if (chatAdapter.chatList.size != 0){
//                recyclerView.visibility = View.VISIBLE
//            }
//        }



        sendButton.setOnClickListener {
            if (editTextMessage.text.toString() != ""){
                val chatMessage = ChatMessage(0, editTextMessage.text.toString())
                chatAdapter.chatList.add(chatMessage)
                chatAdapter.notifyItemInserted(chatAdapter.chatList.size)
//                val text = "\"type\": \"chat_message\", \"message\": \"${chatMessage.text}\""
                editTextMessage.setText("")
            }
            else{
                Toast.makeText(this, "Bạn chưa nhập tin nhắn", Toast.LENGTH_SHORT).show()
            }
        }



    }
}