package com.example.smarthome

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class EditCommand : AppCompatActivity() {
    private lateinit var btnBack:ImageButton
    private lateinit var editTextCommand:EditText
//    private lateinit var recyclerIntent:RecyclerView
    private lateinit var intentAdapter:IntentAdapter
//    private lateinit var intentList:ArrayList<IntentClass>

    private val viewModel:SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_command)

        btnBack = findViewById(R.id.backFromEdit)
        editTextCommand = findViewById(R.id.editTextCommandEdit)



        intentAdapter = IntentAdapter()
//        recyclerIntent.adapter = intentAdapter
        val bun = intent.getBundleExtra("bun")
        val commandNeedEdit = bun?.getParcelable<Command>("commandNeed")
        val position = bun?.getInt("positionNeed")
        Log.e("checkTag", "ewa $position")
        val tokenStr = bun?.getString("tokenStr")!!



        viewModel.refreshIntentList(tokenStr)
        viewModel.arrayListLiveDataIntent.observe(this){response->
            if (response == null){
                return@observe
            }

            intentAdapter.IntentList = response
            val recyclerIntent = findViewById<RecyclerView>(R.id.recycleIntentsEdit)
            recyclerIntent.setHasFixedSize(true)
            recyclerIntent.layoutManager = LinearLayoutManager(this)
            recyclerIntent.adapter = intentAdapter
            intentAdapter.notifyDataSetChanged()


        }

        var str = commandNeedEdit?.intent!!
        str = str.substring(str.indexOf("intent") + 7).dropLast(1)
        Log.e("Check slug", str)
        viewModel.refreshDetailIntent(tokenStr, str)       //Need to fix
        viewModel.detailIntent.observe(this){response->
            if (response == null){
                return@observe
            }
            Log.e("call2", "${intentAdapter.IntentList}")
            for (i in intentAdapter.IntentList.indices){
                if (intentAdapter.IntentList[i] == response){
                    intentAdapter.selected = i
                    intentAdapter.notifyDataSetChanged()
                    break
                }
            }
        }



        editTextCommand.setText(commandNeedEdit.text.toString())




        intentAdapter.onIntentClick = {
            if (it == -1){
                commandNeedEdit?.intent = ""
            }
            else{
                commandNeedEdit?.intent = intentAdapter.IntentList[it].intent_name
            }

        }


        btnBack.setOnClickListener {

            if (editTextCommand.text.trim().toString() == "" && commandNeedEdit?.intent != ""){
                Toast.makeText(this, "Bạn cần xác định lệnh cụ thể", Toast.LENGTH_SHORT).show()
            }
            else if (commandNeedEdit?.intent == "" && editTextCommand.text.trim().toString() != ""){
                Toast.makeText(this, "Bạn cần ghim intent 1 intent cho lệnh", Toast.LENGTH_SHORT).show()
            }
            else{
                val data = Intent()
                val bund = Bundle()
                if (editTextCommand.text.trim().toString() == "" && commandNeedEdit?.intent == ""){
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