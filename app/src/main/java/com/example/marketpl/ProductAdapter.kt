package com.example.marketpl

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketpl.anim.slideRight
import com.example.marketpl.databinding.ItemViewBinding
import com.example.marketpl.dataclass.Product
import com.example.marketpl.datamember.OnItemClickListener
import com.example.marketpl.datamember.ProductManagerImpl
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val context: Context, products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList: MutableList<Product> = products.toMutableList()
    private var itemClickListener: OnItemClickListener? = null

    private var isClickable = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.bind(currentProduct)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("productImage", currentProduct.imageFileName)
            intent.putExtra("productName", currentProduct.productName)
            intent.putExtra("productInfo", currentProduct.productInfo)
            intent.putExtra("userName",currentProduct.sellName)
            intent.putExtra("productPrice", currentProduct.price)
            intent.putExtra("userLoc",currentProduct.address)
            // 여기에 다른 필요한 정보들도 추가할 수 있습니다.
            context.startActivity(intent)
            (context as? Activity)?.slideRight()
        }


    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }


    inner class ProductViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.productImage.setImageResource(product.imageFileName)
            binding.productName.text = product.productName
            binding.productloc.text = product.address
            binding.comentTx.text = product.chatCount.toString()
            binding.favariteTx.text = product.likeCount.toString()
            val priceFormat = NumberFormat.getNumberInstance(Locale.getDefault())
            val formattedPrice = priceFormat.format(product.price)
            val priceText = "$formattedPrice 원"
            binding.productPrice.text = priceText

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
}
