package com.example.smarthome

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.Locale
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.*
import retrofit2.Callback
import java.text.SimpleDateFormat


class WeatherActivity : AppCompatActivity() {
    private lateinit var datetime:TextView
    private lateinit var temp_hour:TextView
    private lateinit var icon_hour: ImageView
    private lateinit var time_hour:TextView
    private lateinit var btnBack:ImageButton

    private val viewModel:SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        viewModel.refreshWeatherForecast()
        btnBack = findViewById(R.id.backbtn)
//        Log.e("kafka", "before token $tokenStr")
        viewModel.weatherForecastData.observe(this) { response ->
            if (response == null) {
                return@observe
            }
            val lct = response.get("location").asJsonObject
            val date = lct["localtime"].toString().substring(1, 11)
            val parse_date = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(date)
            val format_date = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(parse_date).substring(0, 6)
            datetime = findViewById(R.id.date)
            datetime.text = format_date
            val hour:List<String> = lct["localtime"].toString().substring(12, 16).split(":")
            var mid_hour = hour[0].toInt()
            Log.e("kafka", "$hour")
            if (hour[1].toInt() >= 30)  mid_hour += 1
            if (mid_hour < 2) mid_hour = 2
            if (mid_hour > 21) mid_hour = 21

            val forecastday:List<JsonObject> = Gson().fromJson(response["forecast"].asJsonObject["forecastday"], Array<JsonObject>::class.java).asList()
            val today_hours:List<JsonObject> = Gson().fromJson(forecastday[0]["hour"], Array<JsonObject>::class.java).asList()
            val temp_hour_id:List<Int> = listOf(
                R.id.temp_hour0,
                R.id.temp_hour1,
                R.id.temp_hour2,
                R.id.temp_hour3,
                R.id.temp_hour4
                )
            val icon_hour_id:List<Int> = listOf(
                R.id.icon_hour0,
                R.id.icon_hour1,
                R.id.icon_hour2,
                R.id.icon_hour3,
                R.id.icon_hour4
            )
            val time_hour_id:List<Int> = listOf(
                R.id.time_hour0,
                R.id.time_hour1,
                R.id.time_hour2,
                R.id.time_hour3,
                R.id.time_hour4
            )
            val time_forecast_id:List<Int> = listOf(
                R.id.time_forecast0,
                R.id.time_forecast1,
                R.id.time_forecast2
            )
            val temp_forecast_id:List<Int> = listOf(
                R.id.temp_forecast0,
                R.id.temp_forecast1,
                R.id.temp_forecast2
            )
            val icon_forecast_id:List<Int> = listOf(
                R.id.icon_forecast0,
                R.id.icon_forecast1,
                R.id.icon_forecast2
            )
            for (i in (mid_hour-2)..(mid_hour+2))
            {
                val time:String = today_hours[i]["time"].toString().substring(12, 17)
                var icon:String = today_hours[i]["condition"].asJsonObject["icon"].toString()
                icon = icon.substring(1, icon.length-1)
                val temp:String = today_hours[i]["temp_c"].toString()
                temp_hour = findViewById(temp_hour_id[i - (mid_hour-2)])
                icon_hour = findViewById(icon_hour_id[i - (mid_hour-2)])
                time_hour = findViewById(time_hour_id[i - (mid_hour-2)])
                temp_hour.text = temp
                time_hour.text = time
                Log.e("hour: ", "$time $temp $icon")
            }

            for (i in 0..2)
            {
                temp_hour = findViewById(temp_forecast_id[i])
                icon_hour = findViewById(icon_forecast_id[i])
                time_hour = findViewById(time_forecast_id[i])
                val time:String =  forecastday[i]["date"].toString().substring(1, 11)
                val parse_time = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(time)
                val format_time = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(parse_time).substring(0, 6)
                time_hour.text = format_time
                temp_hour.text = forecastday[i]["day"].asJsonObject["avgtemp_c"].toString().plus("°C")
            }
            Log.e("kafka", "")
//            commandAdapter.CommandList = response
//            val recyclerView = findViewById<RecyclerView>(R.id.recycle)
//            recyclerView.setHasFixedSize(true)
//            recyclerView.layoutManager = LinearLayoutManager(this)
//            recyclerView.adapter = commandAdapter
//            commandAdapter.notifyDataSetChanged()
        }
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }

}