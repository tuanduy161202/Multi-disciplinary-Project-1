package com.example.smarthome

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommandList : AppCompatActivity() {

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



        commandAdapter.onItemClick = {
            val intent = Intent(this, EditCommand::class.java)
            intent.putExtra("NeedEditCommand", it)
            startActivity(intent)
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



        btnAddTask.setOnClickListener{
            val nextPage = Intent(this, AddCmd::class.java)
            startActivity(nextPage)
        }


    }


}