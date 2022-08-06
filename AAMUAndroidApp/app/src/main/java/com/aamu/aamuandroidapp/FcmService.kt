package com.aamu.aamuandroidapp

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        //super.onMessageReceived(message)
        Log.i("alertfcm","title"+message.notification?.title.toString())
        Log.i("alertfcm","title"+message.notification?.body.toString())
    }

    override fun onNewToken(token: String) {
        //super.onNewToken(token)
        Log.i("fcmservice","token : "+token)

    }
}