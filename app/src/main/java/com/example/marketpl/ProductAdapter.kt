package com.example.marketpl

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.marketpl.databinding.ItemViewBinding
import com.example.marketpl.dataclass.Product
import com.example.marketpl.datamember.OnItemClickListener
import com.example.marketpl.datamember.ProductManagerImpl
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val context: Context, products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList: List<Product> = ProductManagerImpl.getInstance().getProducts()
    private var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.bind(currentProduct)

        holder.itemView.setOnClickListener {
            // 프래그먼트 전환 코드 실행
            val fragment = DetailFragment()
            val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.to_right,R.anim.from_right)
            transaction.replace(R.id.frameLayout, fragment) // 프래그먼트 컨테이너 ID
            transaction.addToBackStack(null) // 뒤로가기 버튼으로 이전 화면으로 돌아갈 수 있도록 설정
            transaction.commit()
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
    }
}