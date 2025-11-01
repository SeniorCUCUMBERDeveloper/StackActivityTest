package com.example.stackactivitytest.ui

import android.app.KeyguardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.stackactivitytest.R
import com.example.stackactivitytest.util.ColorUtils

class ActivityA : BaseActivity() {

    private lateinit var root: LinearLayout
    private lateinit var et: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            val km = getSystemService(KeyguardManager::class.java)
            km?.requestDismissKeyguard(this, null)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }

        setContent(R.layout.activity_a)
        setScreenTitle("Activity A")

        root = findViewById(R.id.rootA)
        et   = findViewById(R.id.etColor)

        savedInstanceState?.getString(STATE_HEX)?.let { et.setText(it) }
        savedInstanceState?.getInt(STATE_BG)?.let { root.setBackgroundColor(it) }

        findViewById<Button>(R.id.btnGenerate).setOnClickListener {
            val hex = ColorUtils.randomHex()
            et.setText(hex)
            ColorUtils.parse(hex)?.let { c -> root.setBackgroundColor(c) }
        }

        findViewById<Button>(R.id.btnOpenB).setOnClickListener {
            val hex = et.text.toString().trim()
            if (hex.isNotEmpty() && !ColorUtils.isValidHex(hex)) {
                Toast.makeText(this, "Неверный цвет (#RRGGBB)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            startActivity(
                Intent(this, ActivityB::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    if (ColorUtils.isValidHex(hex)) putExtra(EXTRA_HEX, hex)
                }
            )
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(STATE_HEX, et.text.toString())
        outState.putInt(
            STATE_BG,
            (root.background as? android.graphics.drawable.ColorDrawable)?.color ?: 0
        )
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val EXTRA_HEX = "extra_hex"
        private const val STATE_HEX = "state_hex_a"
        private const val STATE_BG  = "state_bg_a"
    }
}
