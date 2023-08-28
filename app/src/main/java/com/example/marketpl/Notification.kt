package com.example.marketpl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class Notification(private val context: Context) {

    fun showNotification() {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "one-channel"
            // ... (채널 설정 등)
            builder = NotificationCompat.Builder(context, channelId)
        } else {
            builder = NotificationCompat.Builder(context)
        }

        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sample4)

        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("새로운 물건이 떴네요")
            setContentText("구매하실건가요?")
            setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        "새로운 물건을 둘러보실라면 들어오세요"
                    )
            )
            setLargeIcon(bitmap)
            setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null)
            )  // hide largeIcon while expanding
            addAction(
                R.mipmap.ic_launcher,
                "Action",
                pendingIntent
            )
        }

        val notificationId = 1 // 알림의 고유한 ID
        manager.notify(notificationId, builder.build()) // 알림 표시
    }
}