package com.wiseowl.woli.data.event.broadcast

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationEventReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if(p1?.action == DownloadManager.ACTION_NOTIFICATION_CLICKED){
            //Handle notification click
        }
    }
}