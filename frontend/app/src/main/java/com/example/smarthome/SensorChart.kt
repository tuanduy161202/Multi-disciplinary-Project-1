package com.example.smarthome

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.androidplot.xy.*
import kotlinx.coroutines.*
import java.text.DecimalFormat
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class SensorChart : AppCompatActivity() {

    private val repository = SharedRepository()

    lateinit var plot: XYPlot
    lateinit var plot2: XYPlot

    private val dateTimeList = mutableListOf<String>()
    private val tempList = mutableListOf<Int>()
    private val humidList = mutableListOf<Int>()

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensor_chart)

        // initialize our XYPlot reference:
        plot = findViewById(R.id.plot)
        plot2 = findViewById(R.id.plot2)

        val tokenStr: String = intent.getStringExtra("tokenStr")!!

        runBlocking {
            var multi = GlobalScope.async(Dispatchers.IO){
                val response = repository.getSensorData("Token $tokenStr")
                Log.e("kafka", "1. sensor data $response")
                if (response != null) {
                    for (instance in response) {
                        dateTimeList.add(instance.date.substring(11, 16))
                        tempList.add(instance.temperature.roundToInt())
                        humidList.add(instance.humidity.roundToInt())
                    }
                    Log.e("kafka", "3. sensor data $dateTimeList")
                }
            }
            multi.await()
            drawGraph()
        }
    }

    private fun drawGraph() {

        val domainLabels = arrayOf<Number>(1, 2, 3, 4, 5, 6, 7, 8)

        Log.e("kafka", "3. sensor data $tempList")

//        val series1 = SimpleXYSeries(
//            Arrays.asList(*domainLabels), Arrays.asList(35, 34, 34, 30, 32, 36, 35, 37),"Temperature"
//        )
        val series1 = SimpleXYSeries(
            Arrays.asList(*domainLabels), tempList,"Temperature"
        )
//        val series2 = SimpleXYSeries(
//            Arrays.asList(*domainLabels), Arrays.asList(60, 61, 60, 55, 58, 65, 61, 60), "Humidity"
//        )

        val series2 = SimpleXYSeries(
            Arrays.asList(*domainLabels), humidList, "Humidity"
        )

        val series1Format = LineAndPointFormatter(Color.BLUE, Color.BLACK, null, null)
        val series2Format = LineAndPointFormatter(Color.DKGRAY, Color.LTGRAY, null, null)

        series1Format.interpolationParams = CatmullRomInterpolator.Params(
            10,
            CatmullRomInterpolator.Type.Centripetal
        )
        series2Format.interpolationParams = CatmullRomInterpolator.Params(
            10,
            CatmullRomInterpolator.Type.Centripetal
        )

        Log.e("kafka", "4. Testing")
        plot.addSeries(series1, series1Format)
        plot2.addSeries(series2, series2Format)

        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = DecimalFormat("#")
        plot.graph.getLineLabelStyle(XYGraphWidget.Edge.LEFT).format = DecimalFormat("#")

        plot2.graph.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).format = DecimalFormat("#")
        plot2.graph.getLineLabelStyle(XYGraphWidget.Edge.LEFT).format = DecimalFormat("#")
    }
}