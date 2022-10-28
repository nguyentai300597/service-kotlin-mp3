package com.example.demoone
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.annotation.Nullable
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat


/**
 * Created by Akinosora on 07/10/2017.
 */
class MusicService : Service() {
    // Kế thừa lớp Service và implement những phương thức cần có của lớp
    var tag = "MusicService"
    var mp: MediaPlayer? = null
    private val CHANNEL_ID = "ForegroundService Kotlin"
    companion object {

        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, ForegroundService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)

        }


    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        // Đôi với Unbound Service thì hàm này không cần quan tâm.
        return null
    }

    // Override phương thức onCreate
    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(
            applicationContext,
            R.raw.saocungduoc
        ) // Khởi tạo trình chơi nhạc mặc định của Android với hai đối số là Context và id của bài hát được đặt trong thư mục raw
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        print("onStartCommand${intent.action}")
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent,  PendingIntent.FLAG_MUTABLE
        )
        val demoIntent = Intent(this, oneActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
       var remoteViews= RemoteViews(
            packageName,
            R.layout.custom_notification
        )
        val artwork= BitmapFactory.decodeResource(resources,R.drawable.ic_launcher_foreground)
        val demoPendingIntent =
            PendingIntent.getActivity(this, 0, demoIntent, PendingIntent.FLAG_MUTABLE)

        val clickInten=Intent(this,ActionReceiver::class.java)
        clickInten.putExtra("inputExtra", "button2")
        val pendingIntentclick = PendingIntent.getBroadcast(
            this,
            0, clickInten,  PendingIntent.FLAG_MUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
         //  .setContent(remoteViews)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL).setLargeIcon(artwork)
            .setContentText(input).setSmallIcon(R.drawable.ic_launcher_tv_foreground).
        setColor(getResources().getColor(R.color.purple_500)).setAutoCancel(true)
            .addAction(R.drawable.ic_action_name,"button1",demoPendingIntent)
            .addAction(R.drawable.ic_launcher_foreground,"button2",pendingIntentclick)
            .addAction(R.drawable.ic_launcher_foreground,"button3",pendingIntent)
//            .addAction(com.google.android.material.R.drawable.ic_m3_chip_checked_circle,"button1",demoPendingIntent)
//            .addAction(com.google.android.material.R.drawable.ic_m3_chip_close,"button2", PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT))
//            .addAction(R.drawable.ic_launcher_foreground,"button3",pendingIntent)
//            .setStyle(androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(1,2,3))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()
        mp!!.start() // Bắt dau chay nhạc


        startForeground(1, notification)

        //stopSelf();
        return START_STICKY
    }


    override fun onDestroy() {
        // Override onDestroy tại đây bạn sẽ viết xử lý khi tiến trình bị hủy
        super.onDestroy()
        mp!!.release() // Dừng chơi nhạc
    }

    override fun startForegroundService(service: Intent?): ComponentName? {
        if (service != null) {
            print("123123123123123${service.action}")
        }
        return super.startForegroundService(service)
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}
