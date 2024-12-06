package com.example.church.client

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import com.facebook.shimmer.ShimmerFrameLayout

class WebviewActivity : ComponentActivity() {

    lateinit var webView:WebView
    lateinit var llShimmerPage:ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        checkAppPermissionSelf()
        setView()
        configurasiWeb()
    }

    private fun checkAppPermissionSelf() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            }
        }
    }

    private fun setView() {
        webView = findViewById(R.id.webView)
        llShimmerPage = findViewById(R.id.llShimmerPage)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configurasiWeb() {
        webView.settings.apply {
            domStorageEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            useWideViewPort = true
            loadWithOverviewMode = true
            blockNetworkLoads = false
            cacheMode = WebSettings.LOAD_DEFAULT
            loadsImagesAutomatically = true
            setRenderPriority(WebSettings.RenderPriority.HIGH)
        }

        webView.webViewClient = object : WebViewClient(){

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                llShimmerPage.startShimmer()
                llShimmerPage.visibility = View.VISIBLE

                webView.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                llShimmerPage.stopShimmer()
                llShimmerPage.visibility = View.GONE

                webView.visibility = View.VISIBLE
            }

        }

        webView.webChromeClient = object : WebChromeClient(){

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)

                Log.d("Progress Web", newProgress.toString())

                if(newProgress == 100){
                    llShimmerPage.visibility = View.GONE
                    llShimmerPage.stopShimmer()

                    webView.visibility = View.VISIBLE
                }
                else{
                    llShimmerPage.visibility = View.VISIBLE
                    llShimmerPage.startShimmer()

                    webView.visibility = View.GONE
                }
            }

        }

        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            val request = android.app.DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimetype)
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Downloading file...")
            request.setTitle(Uri.parse(url).lastPathSegment)
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(android.app.DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(android.os.Environment.DIRECTORY_DOWNLOADS, Uri.parse(url).lastPathSegment)

            val dm = getSystemService(DOWNLOAD_SERVICE) as android.app.DownloadManager
            dm.enqueue(request)


            android.widget.Toast.makeText(this, "Downloading File", android.widget.Toast.LENGTH_LONG).show()
        }

        webView.loadUrl("https://gkiibalige.com") //Change the url string into web url host
    }
}