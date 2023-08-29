package com.example.marketpl.dataclass

import java.io.Serializable

data class Product(
    val imageFileName: Int, val productName: String, val productInfo: String,
    val sellName: String, val price: Int, val address: String, var likeCount: Int,
    val chatCount: Int, var isLike:Boolean
): Serializable
