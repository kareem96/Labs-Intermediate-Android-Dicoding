package com.crm.siska

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.crm.siska.ui.leads.LeadsFragment
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

const val channelName = "com.crm.siska"
private const val CHANNEL_ID = "my_channel"

class   MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val pref = FcmSharedPref(this)
        pref.setTokenFcm(token)
    }

    private fun generateNotification(title: String, description: String){


        val intent = Intent(this, LeadsFragment::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationID = Random.nextInt()

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID))
            .setSmallIcon(R.drawable.ic_notif)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, description))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID), channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(notificationID, builder.build())
    }

    private fun getRemoteView(title: String, description: String): RemoteViews? {
        val remoteView = RemoteViews("com.crm.siska", R.layout.notification)

        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.desc, description)
        remoteView.setImageViewResource(R.id.app_logo, R.drawable.ic_notif)

        return remoteView
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (remoteMessage.notification != null){
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }
    }



}