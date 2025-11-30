package com.mohit.locationlibrary.tracker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.mohit.locationlibrary.data.LocationRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationForegroundService : LifecycleService() {

    companion object {
        const val CHANNEL_ID = "location_channel"
        private const val NOTIF_ID = 1001

        @Volatile
        var isRunning = false
    }

    @Inject
    lateinit var tracker: LocationTracker

    @Inject
    lateinit var repo: LocationRepository

    private var collectJob: Job? = null

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (!isRunning) {
            startForeground(NOTIF_ID, buildNotification())
            isRunning = true
            startCollectingLocations()
        } else {
            // service already running; ensure we are collecting
            if (collectJob == null) startCollectingLocations()
        }
        return START_STICKY
    }

    private fun startCollectingLocations() {
        collectJob?.cancel()
        collectJob = lifecycleScope.launch {
            tracker.locationFlow.collect { location ->
                repo.onNewLocation(location)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        collectJob?.cancel()
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Location Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun buildNotification(): Notification {
        /*val openIntent = Intent(this, MainActivity::class.java) // change to your main activity
        val pendingIntent = TaskStackBuilder.create(this)
            .addNextIntentWithParentStack(openIntent)
            .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)*/

        return NotificationCompat.Builder(this, "location_channel")
            .setContentTitle("Tracking your location")
            .setContentText("Working in background")
            //.setSmallIcon(R.drawable.ic_location)
            //.setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
}
