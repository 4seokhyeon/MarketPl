package com.example.marketpl

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marketpl.anim.slideLeft
import com.example.marketpl.databinding.ActivityMainBinding
import com.example.marketpl.datamember.ProductManagerImpl

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var notificationHelper: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBindings()
        setLocal()
    }

    private fun setBindings() {
        notificationHelper = Notification(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this) //리사이클러뷰 레이아웃 매니저 초기화
        productAdapter = ProductAdapter(this, ProductManagerImpl.getInstance().getProducts()) //ProducManagerImpl의 리스트를 사용
        binding.recyclerView.adapter = productAdapter //라사이클러뷰에 연결하여 화면에 표시
    }

    private fun setLocal() { //동네 설정 프래그먼트
        binding.localBtn.setOnClickListener {
            val fragment = LocalFragment()
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameLayout, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        binding.notifiBtn.setOnClickListener {
            notificationHelper.showNotification()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //디테일 액티비티에서 받은 데이터를 콜백
        super.onActivityResult(requestCode, resultCode, data) //startActivityForResult를 이용해서 구현
        //requestCode와 resultCode를 확인하여 처리
        if (requestCode == ProductAdapter.DETAIL_REQUEST_CODE && resultCode == RESULT_OK) {
            val itemIndex = data?.getIntExtra("itemIndex", -1)
            val isLiked = data?.getBooleanExtra("isLiked", false)
            //받아온 데이터를 기반으로 처리
            if (itemIndex != -1 && isLiked != null) {
                if (itemIndex != null) {
                    productAdapter.notifyItemChanged(itemIndex)
                }
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}
