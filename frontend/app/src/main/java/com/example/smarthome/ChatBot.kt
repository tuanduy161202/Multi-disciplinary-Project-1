package com.example.smarthome

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class ChatBot : AppCompatActivity() {

    private lateinit var btnBack:ImageButton
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextMessage: EditText
    private lateinit var sendButton: ImageButton
//    private lateinit var sf: SharedPreferences
//    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot)

//        btnBack = findViewById(R.id.backFromChat)
        recyclerView = findViewById(R.id.chatRecycler)
        editTextMessage = findViewById(R.id.editTextUserMessage)
        sendButton = findViewById(R.id.sendButton)
//        sf = getSharedPreferences("sfchat", MODE_PRIVATE)
//        editor = sf.edit()



//        btnBack.setOnClickListener {
//            onBackPressed()
//        }


        recyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter()
        recyclerView.adapter = chatAdapter




        sendButton.setOnClickListener {
            if (editTextMessage.text.toString() != ""){
                val sent = editTextMessage.text.toString().toByteArray()
                val utf = String(sent, Charsets.UTF_8)
                val chatMessage = ChatMessage(0, editTextMessage.text.toString())
                chatAdapter.chatList.add(chatMessage)
                chatAdapter.notifyItemInserted(chatAdapter.chatList.size - 1)
                recyclerView.scrollToPosition(chatAdapter.chatList.size - 1)
                editTextMessage.setText("")
                Log.e("User gui", "$chatMessage")
                ham(utf)
//
        }



    }
}
    fun ham(s:String){
        val retrofit = ServiceBuilder.getretrofit().create(APIInterface::class.java)
        val re:RequestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("prompt", s)
            .build()
        retrofit.postChat(re).enqueue(object : Callback<BotResponse> {
            override fun onResponse(call: Call<BotResponse>, response: Response<BotResponse>) {
                val res = response.body()
                if (res != null){
                    val chatResponse = ChatMessage(1, res.message)
                    chatAdapter.chatList.add(chatResponse)
                    chatAdapter.notifyItemInserted(chatAdapter.chatList.size - 1)
                    recyclerView.scrollToPosition(chatAdapter.chatList.size - 1)
                }
            }

            override fun onFailure(call: Call<BotResponse?>, t: Throwable?) {

            }
        })
    }
}