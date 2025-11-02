package com.example.stackactivitytest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.stackactivitytest.ui.ActivityA


class WakeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val i = Intent(context, ActivityA::class.java).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
            )
        }
        context.startActivity(i)
    }
}
