package com.example.smarthome

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddCmd : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var editTextCommand:EditText
    private lateinit var recyclerIntent:RecyclerView
    private lateinit var intentAdapter:IntentAdapter
    private lateinit var intentList:ArrayList<IntentClass>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cmd)

        btnBack = findViewById(R.id.backFromAdd)
        editTextCommand = findViewById(R.id.editTextCommand)
        recyclerIntent = findViewById(R.id.recycleIntents)
        recyclerIntent.setHasFixedSize(true)
        recyclerIntent.layoutManager = LinearLayoutManager(this)



        //TODO: goi api, day la fake data
        intentList = ArrayList()
        intentList.add(IntentClass("Bật đèn", "Bật đèn", 2)) //Den
        intentList.add(IntentClass("Tắt đèn", "Tắt đèn", 2))
        intentList.add(IntentClass("Bật quạt", "Bật quạt", 1))
        intentList.add(IntentClass("Tắt quạt", "Tắt quạt", 1))
        intentList.add(IntentClass("Tưới cây", "Tưới cây", 4))
        intentList.add(IntentClass("Mở rèm", "Mở rèm", 3))
        intentList.add(IntentClass("Đóng rèm", "Đóng rèm", 3))


        intentAdapter = IntentAdapter(intentList)
        recyclerIntent.adapter = intentAdapter

        val newCommand = Command("", "")

        intentAdapter.onIntentClick = {
            if (it == -1){
                newCommand.intent_name = ""
            }
            else{
                newCommand.intent_name = intentAdapter.IntentList[it].intent_name
            }

            Log.i("taggg", "$it and ${newCommand.intent_name}")
        }


        btnBack.setOnClickListener {

            if (editTextCommand.text.trim().toString() == "" && newCommand.intent_name != ""){
                Toast.makeText(this, "Bạn cần xác định lệnh cụ thể", Toast.LENGTH_SHORT).show()
            }
            else if (newCommand.intent_name == "" && editTextCommand.text.trim().toString() != ""){
                Toast.makeText(this, "Bạn cần ghim intent 1 intent cho lệnh", Toast.LENGTH_SHORT).show()
            }
            else{
                val data = Intent()
                if (editTextCommand.text.trim().toString() == "" && newCommand.intent_name == ""){
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