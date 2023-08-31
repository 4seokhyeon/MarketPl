package com.example.marketpl.anim

import android.app.Activity
import android.widget.ImageButton
import com.example.marketpl.R

fun Activity.slideLeft() {
    overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)


}

fun Activity.slideRight() {
    overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
    finish()
}
