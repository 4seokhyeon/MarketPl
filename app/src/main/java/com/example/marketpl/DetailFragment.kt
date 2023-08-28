package com.example.marketpl

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import com.example.marketpl.dataclass.Product

class DetailFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_detail, container, false)
        // 메인 엑티비티의 툴바를 찾아서 숨기기
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.hide()
        return view
    }


    override fun onDestroyView() {
        // 프래그먼트가 제거될 때 툴바 다시 보이기
        val mainActivity = activity as? MainActivity
        mainActivity?.supportActionBar?.show()

        super.onDestroyView()
    }
}