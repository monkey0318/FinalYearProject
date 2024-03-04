package com.example.finalyearproject.event_management

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.finalyearproject.MainActivity
import com.example.finalyearproject.R

class AlarmReceiver : BroadcastReceiver() {
//    override fun onReceive(context: Context?, intent: Intent?) {
//
//        val i = Intent(context, MainActivity::class.java)
//        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        val pendingIntent = PendingIntent.getActivity(context, 0,i,0)
//
//        val builder = NotificationCompat.Builder(context!!, "deeznut")
//            .setSmallIcon(R.drawable.ic_info)
//            .setContentTitle("Last Chance To Register!!")
//            .setContentText("Register now before it end!!")
//            .setAutoCancel(true)
//            .setDefaults(NotificationCompat.DEFAULT_ALL)
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .setContentIntent(pendingIntent)
//
//        val notificationManager = NotificationManagerCompat.from(context)
//        notificationManager.notify(123,builder.build())
//
//
//    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        intent ?: return

        // Check if permission is granted
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE)
            != PackageManager.PERMISSION_GRANTED) {
            // Handle the case where permission is not granted
            return
        }

        val i = Intent(context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        // Create a notification channel for Android 8.0 (API level 26) and higher.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "deeznut",
                "Your Channel Name",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, "deeznut")
            .setSmallIcon(R.drawable.ic_info)
            .setContentTitle("Last Chance To Register!!")
            .setContentText("Register now before it ends!!")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123, builder.build())
    }

}