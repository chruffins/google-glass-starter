package com.example.gghw

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.gghw.ui.theme.GGHWTheme

class MainActivity : ComponentActivity(), GlassGestureDetector.OnGestureListener {
    private lateinit var glassGestureDetector: GlassGestureDetector
    private lateinit var decorView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glassGestureDetector = GlassGestureDetector(this, this)

        decorView = window.decorView
        decorView.setOnSystemUiVisibilityChangeListener { visibility ->
            // If fullscreen mode is lost, reapply it
            if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                hideSystemUI()
            }
        }
        decorView.setBackgroundColor(Color.Transparent.value.toInt())

        enableEdgeToEdge()
        setContent {
            GGHWTheme {
                Scaffold(modifier = Modifier.fillMaxSize().background(Color.Transparent)) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    protected override fun onResume() {
        super.onResume()
        hideSystemUI()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (glassGestureDetector.onTouchEvent(ev!!)) {
            return true
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onGesture(gesture: GlassGestureDetector.Gesture?): Boolean {
        when (gesture) {
            GlassGestureDetector.Gesture.SWIPE_DOWN -> {
                finish()
                return true
            }
            else -> return false
        }
    }

    private fun hideSystemUI() {
        decorView.setSystemUiVisibility(
            (View.SYSTEM_UI_FLAG_IMMERSIVE
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = Color.White
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GGHWTheme {
        Greeting("Android")
    }
}