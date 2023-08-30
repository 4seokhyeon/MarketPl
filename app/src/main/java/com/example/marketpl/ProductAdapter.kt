package com.example.marketpl

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketpl.anim.slideRight
import com.example.marketpl.databinding.ItemViewBinding
import com.example.marketpl.dataclass.Product
import com.example.marketpl.datamember.ProductManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val context: Context, products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList: MutableList<Product> = products.toMutableList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductAdapter.ProductViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        Log.d("Debug", "ProductAdapter - Clicked item index: $position")
        val currentProduct = productList[position]
        holder.bind(currentProduct)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("productImage", currentProduct.imageFileName)
            intent.putExtra("productName", currentProduct.productName)
            intent.putExtra("productInfo", currentProduct.productInfo)
            intent.putExtra("userName", currentProduct.sellName)
            intent.putExtra("productPrice", currentProduct.price)
            intent.putExtra("userLoc", currentProduct.address)
            intent.putExtra("itemIndex", position)
            intent.putExtra("isLiked", currentProduct.isLike) // 추가
            // 여기에 다른 필요한 정보들도 추가할 수 있습니다.
            (context as? Activity)?.startActivityForResult(intent, DETAIL_REQUEST_CODE)
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun getItem(position: Int): Product {
        return productList[position]
    }

    fun getItemIndex(product: Product): Int {
        return productList.indexOf(product)
    }

    inner class ProductViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.productImage.setImageResource(product.imageFileName)
            binding.productName.text = product.productName
            binding.productloc.text = product.address
            val priceFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            val formattedPrice = priceFormat.format(product.price)
            val priceText = "$formattedPrice 원"
            binding.productPrice.text = priceText
            binding.comentTx.text = product.chatCount.toString()
            binding.favariteTx.text = product.likeCount.toString()

            if (product.isLike) {
                binding.likeBtn.setImageResource(R.drawable.like_fill)

            } else {
                binding.likeBtn.setImageResource(R.drawable.like_btn)
            }

            binding.likeBtn.setOnClickListener {
                if (!product.isLike) { // 좋아요 상태가 false일 때만 처리
                    product.isLike = true
                    product.likeCount++
                    binding.likeBtn.setImageResource(R.drawable.like_fill)

                    // 여기서 업데이트된 정보를 다시 메인 엑티비티로 전달하거나 어떤 작업을 수행할 수 있습니다.
                } else {
                    product.isLike = false
                    product.likeCount--
                    binding.likeBtn.setImageResource(R.drawable.like_btn)

                    // 여기서 업데이트된 정보를 다시 메인 엑티비티로 전달하거나 어떤 작업을 수행할 수 있습니다.
                }
            }

        }

        init {
            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentProduct = productList[position]
                    showDeleteConfirmationDialog(currentProduct, position)
                }
                true
            }
        }

        private fun showDeleteConfirmationDialog(product: Product, position: Int) {
            val builder = AlertDialog.Builder(binding.root.context)
            builder.setTitle("삭제하기")
                .setMessage("${product.productName}을(를) 삭제하시겠습니까?")
                .setPositiveButton("삭제하기") { _, _ ->
                    productList.removeAt(position)
                    notifyItemRemoved(position)
                }
                .setNegativeButton("취소", null)
                .show()
        }
    }

    companion object {
        const val DETAIL_REQUEST_CODE = 0 // 원하는 값으로 설정
    }
}
