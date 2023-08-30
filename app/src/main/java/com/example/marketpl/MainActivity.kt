package com.example.marketpl

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketpl.anim.slideLeft
import com.example.marketpl.databinding.ActivityMainBinding
import com.example.marketpl.databinding.ItemViewBinding
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("Debug", "MainActivity - onActivityResult: requestCode=$requestCode, resultCode=$resultCode")

        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ProductAdapter.DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            val itemIndex = data?.getIntExtra("itemIndex", -1)
            val isLiked = data?.getBooleanExtra("isLiked", false)
            if (itemIndex != -1 && isLiked != null) {
                if (itemIndex != null) {

                    productAdapter.notifyItemChanged(itemIndex)
                }
            }
        }
    }


    override fun onBackPressed() {
        finish()
        slideLeft()
    }
//  좋아요
}