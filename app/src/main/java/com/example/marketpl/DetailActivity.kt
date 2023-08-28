package com.example.marketpl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.marketpl.anim.slideLeft

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val productImage = intent.getIntExtra("productImage", 0)
        val productName = intent.getStringExtra("productName")
        val productInfo = intent.getStringExtra("productInfo")
        val selleraddress = intent.getStringExtra("userLoc")
        val sellNameValue = intent.getStringExtra("userName")
        val productPrice = intent.getIntExtra("productPrice", 0)


        val productImageView = findViewById<ImageView>(R.id.detil)
        val productNameTextView = findViewById<TextView>(R.id.product_name)
        val productInfoTextView = findViewById<TextView>(R.id.product_info)
        val selleraddressTextView = findViewById<TextView>(R.id.userlocation)
        val sellNameTextView = findViewById<TextView>(R.id.username)
        val productPriceTextView = findViewById<TextView>(R.id.product_price)

        productImageView.setImageResource(productImage)
        productNameTextView.text = productName
        productInfoTextView.text = productInfo
        selleraddressTextView.text = selleraddress
        sellNameTextView.text = sellNameValue
        productPriceTextView.text = "가격: ${productPrice}원"

        val backButton = findViewById<ImageView>(R.id.detail_back_btn)
        backButton.setOnClickListener{
            onBackPressed()

        }

    }

    override fun onBackPressed() {
        finish()
        slideLeft()
    }
}