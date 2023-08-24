package com.example.marketpl.dataclass

data class Product(
    val imageFileName: Int, val productName: String, val productInfo: String,
    val sellName: String, val price: Int, val address: String, val likeCount: Int,
    val chatCount: Int
)
