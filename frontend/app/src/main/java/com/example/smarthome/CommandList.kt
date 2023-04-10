package com.example.smarthome

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import retrofit2.Callback


class CommandList : AppCompatActivity() {

    //Bien man hinh command list
    private lateinit var btnAddTask:ImageButton
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var commandList:ArrayList<Command>
    private lateinit var commandAdapter: CommandAdapter
    private lateinit var btnBack:ImageButton
    private lateinit var yesButton:CardView
    private lateinit var noButton:CardView
    private lateinit var dialog:Dialog

    private val viewModel:SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.command_list)

        btnAddTask = findViewById(R.id.addTask)
        btnBack = findViewById(R.id.backToHomeFromCommandList)



        Log.e("kafka", "abcd")


        val commandAdapter = CommandAdapter()

        //Da den dc day
//        commandList = ArrayList()
//        commandAdapter = CommandAdapter(commandList)
//        recyclerView.adapter = commandAdapter

        val tokenStr:String = intent.getStringExtra("tokenStr")!!
        viewModel.refreshCommandList("Token $tokenStr")
        Log.e("kafka", "before token $tokenStr")
        viewModel.arrayListLiveData.observe(this){response->
            if (response == null){
                return@observe
            }
            Log.e("kafka", "after token $response")
            commandAdapter.CommandList = response
            val recyclerView = findViewById<RecyclerView>(R.id.recycle)
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = commandAdapter
            commandAdapter.notifyDataSetChanged()


        }


//        Log.e("kafka", "what $commandList")

//        commandList = ArrayList()
//        commandList.add(Command("", 1, "Mở đèn lên cho tôi", "Bật đèn"))
//        commandList.add(Command("", 2, "Tắt đèn cho tôi", "Tắt đèn"))
//        commandList.add(Command("", 3, "Bật quạt lên", "Bật quạt"))
//        commandList.add(Command("", 4, "Tắt quạt", "Tắt quạt"))
//        commandList.add(Command("", 5,"Tưới cây ngoài vườn cho mình", "Bật tưới cây"))
//        commandList.add(Command("", 6,"Tưới cây", "Bật tưới cây"))
//        commandList.add(Command("", 7,"Mở quạt dùm", "Bật quạt"))

//        commandAdapter = CommandAdapter(commandList)
//        recyclerView.adapter = commandAdapter


        val intentLauncher0 =
            registerForActivityResult(PostActivityContract()) { result ->
                if (result != null){
                    val editedCommand = result.getParcelable<Command>("editedCommand")
                    val position = result.getInt("returnPosition")
                    Log.e("anv0", "$editedCommand")
                    if (editedCommand != null){
                        Log.e("anv", "$editedCommand")
                        commandAdapter.CommandList[position] = editedCommand
                        commandAdapter.notifyItemChanged(position)
                    }
                }

            }


        commandAdapter.onItemClick = {
            val bun = Bundle()
            bun.putParcelable("commandNeed", commandAdapter.CommandList[it])
            bun.putInt("positionNeed", it)
            bun.putString("tokenStr", "Token $tokenStr")
            Log.e("checkTag", "com: ${commandAdapter.CommandList[it]}  $it    $bun")
            intentLauncher0.launch(bun)
        }



        commandAdapter.onTrashClick = {
            //Xoa command va hien man hinh are you sure
            val pos:Int = it
            dialog = Dialog(this@CommandList)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.custom_confirm_dialog)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            yesButton = dialog.findViewById(R.id.yes_delete)
            noButton = dialog.findViewById(R.id.no_delete)

            yesButton.setOnClickListener {
                commandAdapter.CommandList.removeAt(pos)
                for (i in commandAdapter.CommandList.indices){
                    commandAdapter.CommandList[i].command_id = i + 1
                }
                commandAdapter.notifyDataSetChanged()
                commandAdapter.notifyItemRemoved(pos)
                dialog.dismiss()
            }

            noButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }



        btnBack.setOnClickListener {
            onBackPressed()
        }

//        val intentLauncher =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//
//                if (result.resultCode == Activity.RESULT_OK) {
//                    val newCommand = result.data?.getParcelableExtra<Command>("newCommand")
//                    if (newCommand != null){
//                        newCommand.command_id = commandAdapter.CommandList.size
//                        commandAdapter.CommandList.add(newCommand)
//                        commandAdapter.notifyItemInserted(commandAdapter.CommandList.size)
//                        Log.i("addcmd", "Them ${newCommand.text}")
//                    }
//
//                }
//
//            }

        val intentLauncher = registerForActivityResult(AddContract()){result->
            if (result != null && result.command_id != -1){
                Log.e("inside", "$result")
                result.command_id = commandAdapter.CommandList.size
                commandAdapter.CommandList.add(result)
                commandAdapter.notifyItemInserted(commandAdapter.CommandList.size)
            }
        }


        btnAddTask.setOnClickListener {
//            val intent = Intent(this, AddCmd::class.java)
            intentLauncher.launch("Token $tokenStr")

        }
    }





}