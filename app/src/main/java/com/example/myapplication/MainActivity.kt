package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // レイアウトを設定
        setContentView(R.layout.activity_main)

        // ComposeViewに通常のComposeコンテンツを設定
        findViewById<ComposeView>(R.id.compose_view).setContent {
            MyApplicationTheme {
                NormalScreen()
            }
        }

        // WebViewの設定
        val webView = WebView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true

            // WebViewを透過させる設定
            setBackgroundColor(Color.TRANSPARENT)

            // HTMLコンテンツをロード
            val htmlData = """
                <html>
                <body>
                    <h1>Hello, WebView!</h1>
                    <p>This is a sample HTML content.</p>
                    <p style="height: 1500px;">This paragraph has a height of 1500px to make sure scrolling is needed.</p>
                    <p>End of content.</p>
                </body>
                </html>
            """.trimIndent()

            loadData(htmlData, "text/html", "UTF-8")
        }

        // Handlerを使用して遅延追加
        Handler(Looper.getMainLooper()).post {
            val windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION, // 一般的なアプリケーションウィンドウとして追加
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // フォーカスを奪わないようにする
                android.graphics.PixelFormat.TRANSLUCENT // 透過を設定
            )

            // WebViewを追加
            windowManager.addView(webView, params)
        }
    }

    @Composable
    fun NormalScreen() {
        Box(modifier = Modifier.fillMaxSize()) {
            // 通常のComposeコンテンツ
            Text(
                text = "This is the normal Compose content.",
                modifier = Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // WebViewの削除を忘れずに
//        val webView = findViewById<WebView>(R.id.web_view)
//        if (webView != null) {
//            (getSystemService(WINDOW_SERVICE) as WindowManager).removeView(webView)
//            webView.destroy()
//        }
    }
}
