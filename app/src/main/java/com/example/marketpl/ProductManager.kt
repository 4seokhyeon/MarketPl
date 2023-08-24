package com.example.marketpl

class ProductManager private constructor() {
    private val productList = mutableListOf<Product>()

    init {
        productList.add(
            Product(
                R.drawable.sample1,
                "산진 한달된 선풍기 팝니다",
                "이사가서 필요가 없어졌어요 급하게 내놓습니다",
                "대현동",
                1000,
                "서울 서대문구 창천동",
                13,
                25
            )
        )
    }
}