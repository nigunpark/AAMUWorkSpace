package com.aamu.aamuandroidapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FcmService : FirebaseMessagingService() {

    private val AAMUCHANNL : String = "AAMUCHANNL"
    private val AAMUNOTIFY : String = "AAMUNOTIFY"

    override fun onMessageReceived(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        for( key in message.data.keys){
            intent.putExtra(key,message.data.get(key))
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)

        val notifibuilder = NotificationCompat.Builder(this,AAMUCHANNL)
            .setSmallIcon(R.drawable.ic_launcher_foreground) //smallIcon
            .setContentTitle(message.notification?.title.toString()) //Title
            .setContentText(message.notification?.body.toString()) //내용
            .setShowWhen(true) //타임 스탬프
            .setAutoCancel(true) //알림 클릭시 알림 제거 여부
            .setContentIntent(pendingIntent) //클릭시 pendingIntent의 Activity로 이동
            .setGroup(AAMUNOTIFY)

        NotificationManagerCompat.from(this).apply {
            notify(100,notifibuilder.build())
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "AAMU_NAME"
            val descriptionText = "일단 아무거나 적어봄"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(AAMUCHANNL, name, importance).apply {
                description = descriptionText
            }
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100,200,300,400,500,400,300,200,100)

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onNewToken(token: String) {
        //super.onNewToken(token)
        Log.i("fcmservice","token : "+token)

    }
}