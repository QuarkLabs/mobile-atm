package com.ivantha.mobileatm.service

import android.os.Handler
import android.os.Looper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ivantha.mobileatm.activity.MainActivity


class FCMService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        val handler = Handler(Looper.getMainLooper())

        if (remoteMessage!!.notification != null) {
            handler.post {
                MainActivity.mainActivity?.showFCM(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
            }
        } else if (remoteMessage.data.size > 0) {
            handler.post {
                MainActivity.mainActivity?.showFCM(remoteMessage.data["title"]!!, remoteMessage.data["body"]!!)
            }
        }
    }
}