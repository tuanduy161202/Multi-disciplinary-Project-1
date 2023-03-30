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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommandList : AppCompatActivity() {

    //Bien man hinh command list
    private lateinit var btnAddTask:ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var commandList:ArrayList<Command>
    private lateinit var commandAdapter: CommandAdapter
    private lateinit var btnBack:ImageButton
    private lateinit var yesButton:CardView
    private lateinit var noButton:CardView
    private lateinit var dialog:Dialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.command_list)

        btnAddTask = findViewById(R.id.addTask)
        btnBack = findViewById(R.id.backToHomeFromCommandList)


        recyclerView = findViewById(R.id.recycle)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)


        //TODO: code lay data tu api, day chi la fake data
        commandList = ArrayList()
        commandList.add(Command("Mở đèn lên cho tôi", "Bật đèn"))
        commandList.add(Command("Tắt đèn cho tôi", "Tắt đèn"))
        commandList.add(Command("Bật quạt lên", "Bật quạt"))
        commandList.add(Command("Tắt quạt", "Tắt quạt"))
        commandList.add(Command("Tưới cây ngoài vườn cho mình", "Bật tưới cây"))
        commandList.add(Command("Tưới cây", "Bật tưới cây"))
        commandList.add(Command("Mở quạt dùm", "Bật quạt"))

        commandAdapter = CommandAdapter(commandList)
        recyclerView.adapter = commandAdapter


        //Intent launcher cho edit command
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
            Log.e("checkTag", "com: ${commandAdapter.CommandList[it]}  $it    $bun")
            intentLauncher0.launch(bun)
//            val intent = Intent(this, EditCommand::class.java)
//            intent.putExtra("position", it)
//            intent.putExtra("commandNeedEdit", commandAdapter.CommandList[it])
//            intentLauncher0.launch(intent)
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
                commandAdapter.notifyItemRemoved(pos)
                dialog.dismiss()
            }

            noButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }



        btnBack.setOnClickListener {
            val next = Intent(this, HomePage::class.java)
            startActivity(next)
        }

        val intentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK) {
                    val newCommand = result.data?.getParcelableExtra<Command>("newCommand")
                    if (newCommand != null){

                        commandAdapter.CommandList.add(newCommand)
                        commandAdapter.notifyItemInserted(commandAdapter.CommandList.size)
                        Log.i("addcmd", "Them ${newCommand.text}")
                    }

                }

            }

        btnAddTask.setOnClickListener {
            val intent = Intent(this, AddCmd::class.java)
            intentLauncher.launch(intent)

        }


    }


}