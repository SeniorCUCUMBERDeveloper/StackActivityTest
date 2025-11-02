package com.example.stackactivitytest.util

import android.graphics.Color
import kotlin.random.Random

object ColorUtils {
    private val HEX = Regex("^#([0-9A-Fa-f]{6})$")

    fun isValidHex(s: String) = HEX.matches(s)
    fun parse(s: String): Int? = if (isValidHex(s)) Color.parseColor(s) else null

    fun randomHex(): String {
        val r = Random.nextInt(0, 256)
        val g = Random.nextInt(0, 256)
        val b = Random.nextInt(0, 256)
        return String.format("#%02X%02X%02X", r, g, b)
    }
}

