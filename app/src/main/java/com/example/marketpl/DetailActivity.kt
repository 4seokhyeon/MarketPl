package com.example.marketpl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marketpl.anim.slideLeft
import com.example.marketpl.anim.slideRight
import com.example.marketpl.databinding.ActivityDetailBinding
import com.example.marketpl.dataclass.Product
import com.example.marketpl.datamember.ProductManagerImpl

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentProduct: Product
    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemIndex = intent.getIntExtra("itemIndex", -1) //index라는 정보를 다져옴
        isLiked = intent.getBooleanExtra("isLiked", true)  // like 버튼의 여부 데이터를 가져옴 좋아요 기능.

        val products = ProductManagerImpl.getInstance().getProducts()  // 싱글톤인 ProductManagerImpl에 대한 리스트를 가져와 products에 저장
        currentProduct = products[itemIndex]  //리스트에 있는 정보를 currentProduct에 저장

        setBindings() //메소드 호
        setLiker()
    }

    private fun setBindings() { //각 내용을 바인딩한 메소드
        binding.apply {
            val product = currentProduct
            detil.setImageResource(product.imageFileName)
            productName.text = product.productName
            productInfo.text = product.productInfo
            userlocation.text = product.address
            username.text = product.sellName
            productPrice.text = "가격: ${product.price}원"
            updateLikeButtonState()
        }
    }

    private fun setLiker() {
        binding.detailBackBtn.setOnClickListener { slideRight() }  //xml 백 버튼

        binding.likeBtn.setOnClickListener {
            isLiked = !isLiked  //변수 뒤집기
            currentProduct.isLike = isLiked //상태를 변경할 때 마다 좋아요 상태 변경
            currentProduct.likeCount += if (isLiked) 1 else -1
            updateLikeButtonState()
            setResultAndFinish()
        }
    }

    private fun updateLikeButtonState() { //좋아요 버튼 변경 메소드
        binding.likeBtn.setImageResource(
            if (isLiked) R.drawable.like_fill
            else R.drawable.like_btn
        )
    }

    private fun setResultAndFinish() {  //액티비티의 결과를 종료 메소드
        val resultIntent = Intent().apply {
            putExtra("itemIndex", intent.getIntExtra("itemIndex", -1))
            putExtra("isLiked", isLiked)
        }
        setResult(Activity.RESULT_OK, resultIntent) //액티비티가 종료되면서 결과를 intent

    }

    override fun onBackPressed() {
        slideLeft()
        finish()
    }
}

