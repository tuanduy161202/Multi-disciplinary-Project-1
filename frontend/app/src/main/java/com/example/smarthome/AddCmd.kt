package com.example.smarthome

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddCmd : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var editTextCommand:EditText
//    private lateinit var recyclerIntent:RecyclerView
    private lateinit var intentAdapter:IntentAdapter
//    private lateinit var intentList:ArrayList<IntentClass>
    private val viewModel:SharedViewModel by lazy{
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cmd)

        btnBack = findViewById(R.id.backFromAdd)
        editTextCommand = findViewById(R.id.editTextCommand)
//        recyclerIntent = findViewById(R.id.recycleIntents)
//        recyclerIntent.setHasFixedSize(true)
//        recyclerIntent.layoutManager = LinearLayoutManager(this)



//        //TODO: goi api, day la fake data
//        intentList = ArrayList()
//        intentList.add(IntentClass("Bật đèn", "Bật đèn", 2)) //Den
//        intentList.add(IntentClass("Tắt đèn", "Tắt đèn", 2))
//        intentList.add(IntentClass("Bật quạt", "Bật quạt", 1))
//        intentList.add(IntentClass("Tắt quạt", "Tắt quạt", 1))
//        intentList.add(IntentClass("Tưới cây", "Tưới cây", 4))
//        intentList.add(IntentClass("Mở rèm", "Mở rèm", 3))
//        intentList.add(IntentClass("Đóng rèm", "Đóng rèm", 3))


        intentAdapter = IntentAdapter()
        val tokenStr = intent.getStringExtra("tokenStr")!!
        viewModel.refreshIntentList(tokenStr)
        viewModel.arrayListLiveDataIntent.observe(this){response->
            if (response == null){
                return@observe
            }
            Log.e("add", "$response")
            intentAdapter.IntentList = response
            val recyclerView = findViewById<RecyclerView>(R.id.recycleIntents)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = intentAdapter
            intentAdapter.notifyDataSetChanged()
        }

        val newCommand = Command("", -1,"", "")

        intentAdapter.onIntentClick = {
            if (it == -1){
                newCommand.intent = ""
            }
            else{
                newCommand.intent = intentAdapter.IntentList[it].intent_name
            }

            Log.i("taggg", "$it and ${newCommand.intent}")
        }


        btnBack.setOnClickListener {

            if (editTextCommand.text.trim().toString() == "" && newCommand.intent != ""){
                Toast.makeText(this, "Bạn cần xác định lệnh cụ thể", Toast.LENGTH_SHORT).show()
            }
            else if (newCommand.intent == "" && editTextCommand.text.trim().toString() != ""){
                Toast.makeText(this, "Bạn cần ghim intent 1 intent cho lệnh", Toast.LENGTH_SHORT).show()
            }
            else{
                val data = Intent()
                if (editTextCommand.text.trim().toString() == "" && newCommand.intent == ""){
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
                else{
                    newCommand.text = editTextCommand.text.trim().toString()
                    data.putExtra("newCommand", newCommand)
                    Log.e("addcmd", "newcmd: ${newCommand.text}")
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }

            }
        }





    }
}