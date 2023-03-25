package com.example.smarthome

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var sf:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private lateinit var button:TextView
    private lateinit var userName:EditText
    private lateinit var passWord:EditText
    private lateinit var checkbox:CheckBox
    private lateinit var register:TextView
    private lateinit var wrongName:TextView
    private lateinit var wrongPass:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()
        button = findViewById(R.id.button)
        userName = findViewById(R.id.EditTextName)
        passWord = findViewById(R.id.EditTextPass)
        checkbox = findViewById(R.id.checkBox)
        register = findViewById((R.id.textViewRegister))



        button.setOnClickListener{
            val name = userName.text.toString()
            val pass = passWord.text.toString()
            if (isEntered(name, pass)){     //Kiem tra them tai khoan sai hay dung
                //TODO: Dang nhap chuyen sang home page
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            }


        }

        checkbox.setOnClickListener {
            //luu lai duoi dang shared preferences
            val name = userName.text.toString()
            val pass = passWord.text.toString()
            if (checkbox.isChecked) {
                if (name != "" && pass != "") {
                    editor.apply {
                        putString("sf_name", name)
                        putString("sf_pass", pass)
                        commit()
                    }
                } else {
                    isEntered(name, pass)
                }
            }
        }

        register.setOnClickListener {
            //TODO
        }
    }

    override fun onResume() {
        super.onResume()

        val name = sf.getString("sf_name", null)
        val pass= sf.getString("sf_pass", null)
        if(pass != "" && name != ""){
            userName.setText(name)
            passWord.setText(pass)
        }
    }

    private fun isEntered(name:String, pass:String):Boolean{
        var flag = true
        if (name == "" && pass == "") {
            flag = false
            Toast.makeText(
                this@MainActivity,
                "Bạn cần nhập tên đăng nhập và mật khẩu",
                Toast.LENGTH_SHORT
            ).show()
        } else if (name == "" && pass != "") {
            flag = false
            Toast.makeText(
                this@MainActivity,
                "Bạn cần nhập tên đăng nhập",
                Toast.LENGTH_SHORT
            ).show()
        } else if (name != "" && pass == ""){
            flag = false
            Toast.makeText(
                this@MainActivity,
                "Bạn cần nhập mật khẩu",
                Toast.LENGTH_SHORT
            ).show()
        }
        return flag
    }
}