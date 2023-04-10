package com.example.smarthome

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton

class UserProfile : AppCompatActivity() {

    private lateinit var btnBack:ImageButton
    private lateinit var editUserName:EditText
    private lateinit var editName:EditText
    private lateinit var editPassword:EditText
    private lateinit var sf:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private lateinit var email:EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        btnBack = findViewById(R.id.backToHome)
        editUserName = findViewById(R.id.userName)
        editPassword = findViewById(R.id.password)
        editName = findViewById(R.id.userNameEdit)
        email = findViewById(R.id.email)

        val username = intent.getStringExtra("username")
        val pass = intent.getStringExtra("password")

        sf = getSharedPreferences("my_sf2", MODE_PRIVATE)
        editor = sf.edit()

        if (username != null && pass != null){
            editUserName.setText(username)
            Log.e("pass", "$pass")
            editPassword.setText(pass.toString())

        }

        btnBack.setOnClickListener {

            editor.apply{
                putString("sf2_name", editName.text.toString())
                putString("sf2_email", email.text.toString())
                commit()
            }
            onBackPressed()
        }

    }

    override fun onResume() {
        super.onResume()
        val name = sf.getString("sf2_name", null)
        val mail = sf.getString("sf2_email", null)

        if (name != null && mail != null){
            editName.setText(name)
            email.setText(mail)
        }
    }

}