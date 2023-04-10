package com.example.smarthome
//
//import android.util.Log
//import okhttp3.Response
//import okhttp3.WebSocket
//import okhttp3.WebSocketListener
//
//class WebsocketListener:WebSocketListener() {
//
//    override fun onOpen(webSocket: WebSocket, response: Response) {
//        super.onOpen(webSocket, response)
//        Log.e("socket-ok", "connected")
//    }
//
//
//    override fun onMessage(webSocket: WebSocket, text: String) {
////        super.onMessage(webSocket, text)
//        Log.e("socket-ok", text)
//
//    }
//
//    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
////        super.onClosing(webSocket, code, reason)
//        webSocket.close(1000, null)
//        Log.e("socket-ok", "$code - $reason")
//    }
//
//    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
////        super.onFailure(webSocket, t, response)
//        Log.e("socket-ok", "${t.message}")
//    }
//
//
//}