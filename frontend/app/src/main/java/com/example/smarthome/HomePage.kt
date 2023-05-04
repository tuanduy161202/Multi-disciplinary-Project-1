package com.example.smarthome

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import okhttp3.*

class HomePage : AppCompatActivity() {
    //User button
    private lateinit var user:ImageView

    //Action bar button
    private lateinit var chatBotButton:ImageButton
    private lateinit var chartButton:ImageButton
    private lateinit var listCommandButton:ImageButton
    private lateinit var weatherButton:ImageButton

    private lateinit var humid:TextView
    private lateinit var temp:TextView

    //Toggle switch button
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var lampSwitch:Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var fanSwitch:Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var waterSwitch: Switch
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var curtainSwitch: Switch


    private lateinit var lampTile:RelativeLayout
    private lateinit var fanTile:RelativeLayout
    private lateinit var waterTile:RelativeLayout
    private lateinit var curtainTile:RelativeLayout

    private lateinit var progressBar:ProgressBar

    private lateinit var sf: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var phut:String
    private lateinit var giay:String
    lateinit var ws:WebSocket
    lateinit var animator:ObjectAnimator

    private val viewModel:SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        user = findViewById(R.id.user)

        lampSwitch = findViewById(R.id.lampSwitch)
        fanSwitch = findViewById(R.id.fanSwitch)
        waterSwitch = findViewById(R.id.waterSwitch)
        curtainSwitch = findViewById(R.id.curtainSwitch)

        lampTile = findViewById(R.id.LampTile)
        fanTile = findViewById(R.id.FanTile)
        waterTile = findViewById(R.id.WaterTile)
        curtainTile = findViewById(R.id.CurtainTile)

        chatBotButton = findViewById(R.id.chatBot)
        chartButton = findViewById(R.id.chart)
        weatherButton = findViewById(R.id.weather)
        listCommandButton = findViewById(R.id.listCommand)

        progressBar = findViewById(R.id.progressBar)
        temp = findViewById(R.id.temp)
        humid = findViewById(R.id.humid)


        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()

        val request = Request.Builder().url("ws://10.0.2.2:8000/ws/socket-server/").build()
        val listener = WebsocketListener(viewModel)
        val client = OkHttpClient()
        ws = client.newWebSocket(request, listener)
        SharedViewModel.socket = ws


        viewModel.sensorLiveData.observe(this) { data ->
            if (data == null) return@observe

            Log.e("socket-okkk", "$data")

            if (data["type"].toString() == "\"sensor_data\"") {
                val humid_ = data["humid_data"].toString()
                val temp_ = data["temp_data"].toString()
                humid.text = humid_.substring(1, humid_.length - 1).plus("%")
                temp.text = temp_.substring(1, temp_.length - 1).plus("°C")
//                Log.e("setData", "${humid.text} and ${temp.text}")
            }
        }


        viewModel.statusLiveData.observe(this){data ->
            if (data == null) return@observe

            if (data["type"].toString() == "\"update_status\""){
                Log.e("socket-ok", "light ${data["device"]}")

                when (data["device"].toString()){
                    "\"light\"" -> {
                        if (data["status"].toString() == "\"on\""){
                            lampTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                            if (!lampSwitch.isChecked) lampSwitch.isChecked = true

                        }
                        else{
                            Log.e("backToPhone", "${data["status"].toString()=="\"off\""}")
                            lampTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                            if (lampSwitch.isChecked) lampSwitch.isChecked = false
                            Log.e("backToPhone", "Cannot see")
                        }
                    }

                    "\"fan\"" -> {
                        if (data["status"].toString() == "\"on\""){
                            fanTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                            if (!fanSwitch.isChecked) fanSwitch.isChecked = true
                        }
                        else{
                            fanTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                            if (fanSwitch.isChecked) fanSwitch.isChecked = false
                        }
                    }

                    "\"watering\"" -> {
                        if (data["status"].toString() == "\"on\""){
                            waterTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                            if (!waterSwitch.isChecked) waterSwitch.isChecked = true
//                            progressBar.visibility = View.VISIBLE

                        }
                        else{
                            waterTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                            if (waterSwitch.isChecked) waterSwitch.isChecked = false
//                            progressBar.visibility = View.INVISIBLE
                        }
                    }

                    "\"curtain\"" -> {
                        if (data["status"].toString() == "\"on\""){
                            curtainTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                            if (!curtainSwitch.isChecked) curtainSwitch.isChecked = true
                        }
                        else{
                            curtainTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                            if (curtainSwitch.isChecked) curtainSwitch.isChecked = false
                        }
                    }
                }
            }
        }









        user.setOnClickListener {
            val inten = Intent(this, UserProfile::class.java)
            val username = intent.getStringExtra("username")
            val pass = intent.getStringExtra("password")
            inten.putExtra("username", username)
            inten.putExtra("password", pass)
            startActivity(inten)
        }


        lampSwitch.setOnClickListener{
            if (lampSwitch.isChecked){
                lampTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                //TODO: code bat den
                val text = "{\"type\": \"turn_on\", \"device\": \"light\"}"
                ws.send(text)
            }
            else{
                lampTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                //TODO: code tat den
                val text = "{\"type\": \"turn_off\", \"device\": \"light\"}"
                ws.send(text)
            }
        }

        fanSwitch.setOnClickListener{
            if (fanSwitch.isChecked){
                fanTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                //TODO: code bat quat
                val text = "{\"type\": \"turn_on\", \"device\": \"fan\"}"
                ws.send(text)
            }
            else{
                fanTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                //TODO: code tat quat
                val text = "{\"type\": \"turn_off\", \"device\": \"fan\"}"
                ws.send(text)
            }
        }

        waterSwitch.setOnClickListener{
            if (waterSwitch.isChecked){
                waterTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                //TODO: code bat tuoi nuoc
                val text = "{\"type\": \"turn_on\", \"device\": \"watering\"}"
                ws.send(text)


//                progressBar.visibility = View.VISIBLE
                progressBar.max = toDuration(phut.toInt(), giay.toInt())
                var curr = progressBar.max
                var duration:Long = toDuration(phut.toInt(), giay.toInt()).toLong()
                animator = ObjectAnimator.ofInt(progressBar, "progress", curr).setDuration(duration)
                animator.doOnEnd {
                    progressBar.visibility = View.INVISIBLE
                    if (waterSwitch.isChecked) {
                        waterSwitch.isChecked = false
                        waterTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                        val endText = "{\"type\": \"turn_off\", \"device\": \"watering\"}"
                        ws.send(endText)
                    }

                }
                animator.start()
            }
            else{
                waterTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                progressBar.visibility = View.INVISIBLE
                //TODO: code tat tuoi nuoc
                val text = "{\"type\": \"turn_off\", \"device\": \"watering\"}"
                ws.send(text)
                animator.end()
            }
        }

        waterTile.setOnClickListener {
            val sheet = findViewById<RelativeLayout>(R.id.sheet)
            val dialog = BottomSheetDialog(this, R.style.BottomSheetStyle )
            val view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_idialog,
                sheet
            )


            val okButton = view.findViewById<ImageButton>(R.id.checkIcon)
            val minusButton = view.findViewById<ImageButton>(R.id.minusIcon)
            val plusButton = view.findViewById<ImageButton>(R.id.plusIcon)
            val minute = view.findViewById<TextView>(R.id.minute)
            val second = view.findViewById<TextView>(R.id.second)

            minute.text = phut
            second.text = giay

            okButton.setOnClickListener {
                val text = "{\"type\": \"set_timer\", \"minute\": $phut, \"second\": ${giay.toInt()}}"
                Log.e("sendText", text)
                ws.send(text)
                dialog.dismiss()
            }

            minusButton.setOnClickListener {

                minute.text = phut
                second.text = giay

                var minuteNum = phut.toInt()
                var secondNum = giay.toInt()

                secondNum -= 10
                if (secondNum < 0){
                    minuteNum -= 1
                    secondNum = if (minuteNum >= 0) 50 else 0

                    if  (minuteNum <= 0){
                        minuteNum = 0
                    }
                }
                second.text = if (secondNum == 0) "00" else secondNum.toString()
                giay = second.text.toString()
                minute.text = minuteNum.toString()
                phut = minute.text.toString()

                editor.apply {
                    putString("sf_minute", minute.text.toString())
                    putString("sf_second", second.text.toString())
                    commit()
                }

            }

            plusButton.setOnClickListener {

                minute.text = phut
                second.text = giay

                var minuteNum = phut.toInt()
                var secondNum = giay.toInt()

                secondNum += 10
                if (secondNum == 60){
                    minuteNum += 1
                    secondNum = 0

                    if  (minuteNum >= 30){
                        minuteNum = 30
                    }
                }
                second.text = if (secondNum == 0) "00" else secondNum.toString()
                giay = second.text.toString()
                minute.text = minuteNum.toString()
                phut = minute.text.toString()

                editor.apply {
                    putString("sf_minute", minute.text.toString())
                    putString("sf_second", second.text.toString())
                    commit()
                }

            }

            dialog.setCancelable(false)
            dialog.setContentView(view)
            dialog.show()




        }



        curtainSwitch.setOnClickListener{
            if (curtainSwitch.isChecked){
                curtainTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                //TODO: code mo rem
                val text = "{\"type\": \"turn_on\", \"device\": \"curtain\"}"
                ws.send(text)
            }
            else{
                curtainTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                //TODO: code dong rem
                val text = "{\"type\": \"turn_off\", \"device\": \"curtain\"}"
                ws.send(text)
            }
        }

        //Action bar
        chatBotButton.setOnClickListener{
            //TODO: chuyen man hinh
            val intent = Intent(this, ChatBot::class.java)
            startActivity(intent)
        }

        chartButton.setOnClickListener {
            //TODO: chuyen man hinh
//            val intent = Intent(this, ChatBot::class.java)
//            startActivity(intent)
        }

        weatherButton.setOnClickListener {
            //TODO: chuyen man hinh
        }

        listCommandButton.setOnClickListener {
            //TODO: chuyen man hinh
            val tokenStr:String = intent.getStringExtra("TokenStr")!!
            val inten = Intent(this, CommandList::class.java)
            inten.putExtra("tokenStr", tokenStr)
//            Log.e("contisend", tokenStr)
            Log.e("kafka", "startactivity")
            startActivity(inten)
        }

        //TODO: update temperature and humidity

    }

    override fun onResume() {
        super.onResume()
        val phutStr = sf.getString("sf_minute", null)
        val giayStr = sf.getString("sf_second", null)
        phut = phutStr.toString()
        giay = giayStr.toString()
        Log.i("myTag", "onResume before if $phut $giay")

//        phut = "15"
//        giay = "00"

        if (phut == "null" && giay == "null"){
            phut = "2"
            giay = "00"
            Log.i("myTag", "onResume $phut $giay")

        }

    }

    private fun toDuration(min:Int, sec:Int):Int{
        return (min * 60 + sec) * 1000
    }

}