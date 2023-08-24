package com.example.marketpl

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketpl.databinding.ItemViewBinding
import com.example.marketpl.dataclass.Product
import com.example.marketpl.datamember.ProductManagerImpl
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter(private val context: Context, products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val productList: List<Product> = ProductManagerImpl.getInstance().getProducts()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ProductViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ProductViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.bind(currentProduct)
    }

    override fun getItemCount(): Int {
        return productList.size
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

        }
    }
}