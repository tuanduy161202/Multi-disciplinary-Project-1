package com.example.smarthome

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch

class HomePage : AppCompatActivity() {
    //User button
    private lateinit var user:ImageButton

    //Action bar button
    private lateinit var chatBot:ImageButton
    private lateinit var chart:ImageButton
    private lateinit var listCommand:ImageButton

    //Tile

    //Toggle switch button
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var lampSwitch:Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var fanSwitch:Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var waterSwitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var curtainSwitch: Switch





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
    }
}