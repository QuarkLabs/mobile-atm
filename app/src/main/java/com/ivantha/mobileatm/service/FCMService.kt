package com.ivantha.mobileatm.service

import android.os.Handler
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.os.Looper




class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val handler = Handler(Looper.getMainLooper())

        if (remoteMessage!!.notification != null) {
            handler.post {
                Toast.makeText(applicationContext, "Message Notification Body: " + remoteMessage.notification!!.body, Toast.LENGTH_LONG).show()
            }
        }

        if (remoteMessage.data.size > 0) {
            handler.post {
                Toast.makeText(applicationContext, "Message data payload: " + remoteMessage.data, Toast.LENGTH_LONG).show()
            }
        }
    }
}