package com.example.stackactivitytest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.example.stackactivitytest.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var contentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)

        setContentView(R.layout.activity_base)

        tvTitle = findViewById(R.id.tvTitle)
        contentContainer = findViewById(R.id.contentContainer)
    }

    protected fun setContent(@LayoutRes layoutId: Int) {
        val child: View = LayoutInflater.from(this)
            .inflate(layoutId, contentContainer, false)
        contentContainer.removeAllViews()
        contentContainer.addView(child)
    }

    protected fun setScreenTitle(title: CharSequence) {
        tvTitle.text = title
    }
}

