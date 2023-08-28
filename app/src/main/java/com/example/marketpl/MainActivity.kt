package com.example.marketpl

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketpl.databinding.ActivityMainBinding
import com.example.marketpl.datamember.ProductManagerImpl

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var notificationHelper: Notification
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        notificationHelper = Notification(this)


        binding.localBtn.setOnClickListener {
            val fragment = LocalFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val productManager = ProductManagerImpl.getInstance()
        val products = productManager.getProducts()

        productAdapter = ProductAdapter(this, products)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = productAdapter

        val notifiBtn: ImageView = findViewById(R.id.notifi_btn)
        notifiBtn.setOnClickListener {
            notificationHelper.showNotification()
        }
    }

}