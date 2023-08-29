package com.example.marketpl
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.marketpl.ProductAdapter
import com.example.marketpl.databinding.ActivityDetailBinding
import com.example.marketpl.datamember.ProductManagerImpl

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productImage = intent.getIntExtra("productImage", 0)
        val productName = intent.getStringExtra("productName")
        val productInfo = intent.getStringExtra("productInfo")
        val sellerAddress = intent.getStringExtra("userLoc")
        val sellNameValue = intent.getStringExtra("userName")
        val productPrice = intent.getIntExtra("productPrice", 0)
        val itemIndex = intent.getIntExtra("itemIndex", -1)

        binding.detil.setImageResource(productImage)
        binding.productName.text = productName
        binding.productInfo.text = productInfo
        binding.userlocation.text = sellerAddress
        binding.username.text = sellNameValue
        binding.productPrice.text = "가격: ${productPrice}원"

        binding.detailBackBtn.setOnClickListener {
            onBackPressed()
        }

        binding.likeBtn.setOnClickListener {
            val newLikeState = !binding.likeBtn.isSelected
            binding.likeBtn.isSelected = newLikeState

            if (itemIndex != -1) {
                val currentProduct = ProductManagerImpl.getInstance().getProducts()[itemIndex]
                currentProduct.isLike = newLikeState

                if (newLikeState) {
                    currentProduct.likeCount++
                } else {
                    currentProduct.likeCount--
                }
                productAdapter.notifyItemChanged(itemIndex)
            }
        }
    }

    override fun onBackPressed() {
        finish()

    }
}
