package com.example.smarthome

import android.util.Log
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class WebsocketListener(val viewModel: SharedViewModel): WebSocketListener() {

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        Log.e("socket-ok", "connected")
    }


    override fun onMessage(webSocket: WebSocket, text: String) {
//        Log.e("socket-ok", text)
        val data = Json.parseToJsonElement(text).jsonObject
        when (data["type"].toString()){
            "\"sensor_data\"" -> {
                viewModel.refreshSensorData(data)
            }

            "\"update_status\"" -> {
                viewModel.refreshStatus(data)
            }

        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
//        super.onClosing(webSocket, code, reason)
        webSocket.close(1000, null)
        Log.e("socket-ok", "$code - $reason")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//        super.onFailure(webSocket, t, response)
        Log.e("socket-ok", "${t.message}")
    }
}