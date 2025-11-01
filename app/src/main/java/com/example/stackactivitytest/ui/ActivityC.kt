package com.example.stackactivitytest.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.stackactivitytest.R

class ActivityC : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(R.layout.activity_c)
        setScreenTitle("Activity C")

        findViewById<Button>(R.id.btnOpenA).setOnClickListener {
            startActivity(
                Intent(this, ActivityA::class.java).apply {
                    addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_SINGLE_TOP
                    )
                }
            )
        }
    }
}
