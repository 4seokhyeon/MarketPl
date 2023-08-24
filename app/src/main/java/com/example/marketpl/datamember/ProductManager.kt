package com.example.marketpl.datamember

import com.example.marketpl.dataclass.Product

interface ProductManager {
    fun getProducts(): List<Product>
}