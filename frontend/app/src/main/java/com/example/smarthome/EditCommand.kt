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

class EditCommand : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var editTextCommand:EditText
    private lateinit var recyclerIntent:RecyclerView
    private lateinit var intentAdapter:IntentAdapter
    private lateinit var intentList:ArrayList<IntentClass>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_command)

        btnBack = findViewById(R.id.backFromEdit)
        editTextCommand = findViewById(R.id.editTextCommandEdit)
        recyclerIntent = findViewById(R.id.recycleIntentsEdit)
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

        val bun = intent.getBundleExtra("bun")
        val commandNeedEdit = bun?.getParcelable<Command>("commandNeed")
        val position = bun?.getInt("positionNeed")
        Log.e("checkTag", "ewa $position")
        editTextCommand.setText(commandNeedEdit?.text.toString())

        for (i in intentList.indices){
            if (intentList[i].intent_name == commandNeedEdit?.intent_name){
                intentAdapter.selected = i
                intentAdapter.notifyDataSetChanged()
                break
            }
        }


        intentAdapter.onIntentClick = {
            if (it == -1){
                commandNeedEdit?.intent_name = ""
            }
            else{
                commandNeedEdit?.intent_name = intentAdapter.IntentList[it].intent_name
            }

        }


        btnBack.setOnClickListener {

            if (editTextCommand.text.trim().toString() == "" && commandNeedEdit?.intent_name != ""){
                Toast.makeText(this, "Bạn cần xác định lệnh cụ thể", Toast.LENGTH_SHORT).show()
            }
            else if (commandNeedEdit?.intent_name == "" && editTextCommand.text.trim().toString() != ""){
                Toast.makeText(this, "Bạn cần ghim intent 1 intent cho lệnh", Toast.LENGTH_SHORT).show()
            }
            else{
                val data = Intent()
                val bund = Bundle()
                if (editTextCommand.text.trim().toString() == "" && commandNeedEdit?.intent_name == ""){
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                }
                else if (position != null && commandNeedEdit != null){
                    commandNeedEdit.text = editTextCommand.text.trim().toString()
                    bund.putInt("returnPosition", position)
                    bund.putParcelable("editedCommand", commandNeedEdit)
                    data.putExtra("bundle", bund)
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }

            }
        }





    }
}