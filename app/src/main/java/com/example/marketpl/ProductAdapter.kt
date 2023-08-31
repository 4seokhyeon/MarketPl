package com.example.marketpl

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketpl.databinding.ItemViewBinding
import com.example.marketpl.dataclass.Product

class ProductAdapter(private val context: Context, products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList: MutableList<Product> = products.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.bind(currentProduct)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("productImage", currentProduct.imageFileName)
                putExtra("productName", currentProduct.productName)
                putExtra("productInfo", currentProduct.productInfo)
                putExtra("userName", currentProduct.sellName)
                putExtra("productPrice", currentProduct.price)
                putExtra("userLoc", currentProduct.address)
                putExtra("itemIndex", position)
                putExtra("isLiked", currentProduct.isLike)
            }
            (context as? Activity)?.startActivityForResult(intent, DETAIL_REQUEST_CODE) // 어댑터에서의 정보를 선언해주며 데이터를 데테일 엑티비티로 넘겨줌
        }
    }

    override fun getItemCount(): Int = productList.size //리스트의 갯수만큼 보여준다

    inner class ProductViewHolder(private val binding: ItemViewBinding) : //item xml에 데이터를 맞게 띄워줌
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                productImage.setImageResource(product.imageFileName)
                productName.text = product.productName
                productloc.text = product.address
                productPrice.text = "${product.price} 원"
                comentTx.text = product.chatCount.toString()
                favariteTx.text = product.likeCount.toString()

                likeBtn.setImageResource(
                    if (product.isLike) R.drawable.like_fill
                    else R.drawable.like_btn
                )

                likeBtn.setOnClickListener {
                    product.isLike = !product.isLike // 변수 뒤집기
                    if (product.isLike) product.likeCount++ //isLike의 유뮤를 확인하여 count를 증,감소
                    else product.likeCount--
                    notifyDataSetChanged()
                }
            }
        }

        init {
            binding.root.setOnLongClickListener {//롱클릭 이벤트 리스트 삭제 기능
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentProduct = productList[position]
                    showDeleteConfirmationDialog(currentProduct, position)
                }
                true
            }
        }

        private fun showDeleteConfirmationDialog(product: Product, position: Int) { // 리스트 삭제 메소드
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
        const val DETAIL_REQUEST_CODE = 0
    }
}
