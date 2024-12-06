package id.gkii.church.client

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.gkii.church.client.R

class MainActivity : ComponentActivity() {
    lateinit var tvVersionName:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findIdView()

        setSplashTime()
    }

    @SuppressLint("SetTextI18n")
    private fun findIdView() {
        tvVersionName = findViewById(R.id.tvVersionName)

        val packageInfo = applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0)
        tvVersionName.text = "V${packageInfo.versionName}"
    }

    private fun setSplashTime() {
        Handler(Looper.myLooper()!!).postDelayed({
            val intent = Intent(this, WebviewActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000L)
    }
}