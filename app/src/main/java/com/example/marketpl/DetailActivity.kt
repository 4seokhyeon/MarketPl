package com.example.marketpl

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.marketpl.anim.slideLeft
import com.example.marketpl.anim.slideRight
import com.example.marketpl.databinding.ActivityDetailBinding
import com.example.marketpl.dataclass.Product
import com.example.marketpl.datamember.ProductManagerImpl
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var currentProduct: Product
    private var isLiked = false
    private lateinit var coordinatorLayout: CoordinatorLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val itemIndex = intent.getIntExtra("itemIndex", -1) //index라는 정보를 다져옴
        isLiked = intent.getBooleanExtra("isLiked", true)  // like 버튼의 여부 데이터를 가져옴 좋아요 기능.

        val products = ProductManagerImpl.getInstance().getProducts()  // 싱글톤인 ProductManagerImpl에 대한 리스트를 가져와 products에 저장
        currentProduct = products[itemIndex]  //리스트에 있는 정보를 currentProduct에 저장

        coordinatorLayout = binding.coordinatorLayout

        setBindings()
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

            val snackbar = if (isLiked) "좋아요를 눌렀습니다" else "좋아요를 취소했습니다."
            showSnackbar(snackbar)
        }
    }

    private fun updateLikeButtonState() { //좋아요 버튼 변경 메소드
        binding.likeBtn.setImageResource(
            if (isLiked) {
                R.drawable.like_fill
            }
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

    private fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_SHORT) // 짧은 지속 시간으로 변경
        snackbar.show()

        // 3초 후에 스낵바 사라지도록 설정
        Handler(Looper.getMainLooper()).postDelayed({
            snackbar.dismiss()
        }, 3000)
    }

    override fun onBackPressed() {
        slideLeft()
        finish()
    }
}

