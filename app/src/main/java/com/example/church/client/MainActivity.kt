package com.example.church.client

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSplashTime()
    }

    private fun setSplashTime() {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, WebviewActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000L)
    }
}