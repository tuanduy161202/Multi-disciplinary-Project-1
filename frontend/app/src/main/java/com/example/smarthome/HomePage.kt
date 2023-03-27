package com.example.smarthome

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
import com.google.android.material.bottomsheet.BottomSheetDialog

class HomePage : AppCompatActivity() {
    //User button
    private lateinit var user:ImageButton

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)


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

        lampSwitch.setOnClickListener{
            if (lampSwitch.isChecked){
                lampTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                //TODO: code bat den
            }
            else{
                lampTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                //TODO: code tat den
            }
        }

        fanSwitch.setOnClickListener{
            if (fanSwitch.isChecked){
                fanTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                //TODO: code bat quat
            }
            else{
                fanTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                //TODO: code tat quat
            }
        }

        waterSwitch.setOnClickListener{
            if (waterSwitch.isChecked){
                waterTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffffff"))
                //TODO: code bat tuoi nuoc


                progressBar.visibility = View.VISIBLE
                progressBar.max = 100
                var curr = 60
                var duration:Long = 60000
                ObjectAnimator.ofInt(progressBar, "progress", curr).setDuration(duration).start()
            }
            else{
                waterTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                progressBar.visibility = View.INVISIBLE
                //TODO: code tat tuoi nuoc
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
            }
            else{
                curtainTile.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#b3ffffff"))
                //TODO: code dong rem
            }
        }

        //Action bar
        chatBotButton.setOnClickListener{
            //TODO: chuyen man hinh
            //val intent = Intent(this, trang_can_den)
            //startActivity(intent)
        }

        chartButton.setOnClickListener {
            //TODO: chuyen man hinh
            //val intent = Intent(this, trang_can_den)
            //startActivity(intent)
        }

        weatherButton.setOnClickListener {
            //TODO: chuyen man hinh
        }

        listCommandButton.setOnClickListener {
            //TODO: chuyen man hinh
            val intent = Intent(this, CommandList::class.java)
            startActivity(intent)
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