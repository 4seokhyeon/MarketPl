package com.example.marketpl

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.marketpl.databinding.ActivityDetailBinding
import com.example.marketpl.dataclass.Product
import com.example.marketpl.datamember.ProductManagerImpl

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var productAdapter: ProductAdapter
    private var currentProduct: Product? = null
    private var itemIndex = 0
    private var isLiked = false
    private var isLikeButtonClicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemIndex = intent.getIntExtra("itemIndex", -1)
        isLiked = intent.getBooleanExtra("isLiked", false)

        val products = ProductManagerImpl.getInstance().getProducts()
        productAdapter = ProductAdapter(this, products)

        currentProduct = productAdapter.getItem(itemIndex)
        updateLikeButtonState()

        val productImage = intent.getIntExtra("productImage", 0)
        val productName = intent.getStringExtra("productName")
        val productInfo = intent.getStringExtra("productInfo")
        val sellerAddress = intent.getStringExtra("userLoc")
        val sellNameValue = intent.getStringExtra("userName")
        val productPrice = intent.getIntExtra("productPrice", 0)

        binding.detil.setImageResource(productImage)
        binding.productName.text = productName
        binding.productInfo.text = productInfo
        binding.userlocation.text = sellerAddress
        binding.username.text = sellNameValue
        binding.productPrice.text = "가격: ${productPrice}원"

        productAdapter = ProductAdapter(this, products)

        binding.detailBackBtn.setOnClickListener {
            onBackPressed()
        }

        binding.likeBtn.setImageResource(
            if (isLiked) R.drawable.like_fill
            else R.drawable.like_btn
        )


        binding.likeBtn.setOnClickListener {


            currentProduct?.let {
                it.isLike = true
                it.likeCount++
                isLiked = true
                updateLikeButtonState()

                val intent = Intent()
                intent.putExtra("itemIndex", itemIndex)
                intent.putExtra("isLiked", isLiked)
                setResult(Activity.RESULT_OK, intent)
            }

        }
    }


    private fun updateLikeButtonState() {
        binding.likeBtn.setImageResource(
            if (isLiked) R.drawable.like_fill
            else R.drawable.like_btn
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ProductAdapter.DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedItemIndex = data?.getIntExtra("itemIndex", -1)
            val updatedIsLiked = data?.getBooleanExtra("isLiked", false)

            if (updatedItemIndex != -1 && updatedIsLiked != null) {
                if (updatedItemIndex == itemIndex) { // 현재 디테일 페이지와 관련된 업데이트일 경우에만 처리
                    isLiked = updatedIsLiked
                    updateLikeButtonState()
                }
            }
        }
    }

    override fun onBackPressed() {
        intent.putExtra("itemIndex", itemIndex)
        intent.putExtra("isLiked", isLiked)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}

