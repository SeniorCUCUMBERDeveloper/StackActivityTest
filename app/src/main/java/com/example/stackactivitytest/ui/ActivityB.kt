package com.example.stackactivitytest.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import com.example.stackactivitytest.R
import com.example.stackactivitytest.ui.ActivityA.Companion.EXTRA_HEX
import com.example.stackactivitytest.util.ColorUtils

class ActivityB : BaseActivity() {

    private lateinit var root: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent(R.layout.activity_b)
        setScreenTitle("Activity B")

        root = findViewById(R.id.rootB)

        intent.getStringExtra(EXTRA_HEX)?.let { hex ->
            ColorUtils.parse(hex)?.let { root.setBackgroundColor(it) }
        }
        savedInstanceState?.getInt(STATE_BG)?.let { root.setBackgroundColor(it) }

        findViewById<Button>(R.id.btnOpenC).setOnClickListener {
            startActivity(Intent(this, ActivityC::class.java))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(
            STATE_BG,
            (root.background as? android.graphics.drawable.ColorDrawable)?.color ?: 0
        )
        super.onSaveInstanceState(outState)
    }

    companion object {
        private const val STATE_BG = "state_bg_b"
    }
}
