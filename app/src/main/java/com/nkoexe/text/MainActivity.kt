package com.nkoexe.text

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.nkoexe.text.ui.DisplayScreen
import com.nkoexe.text.ui.HomeScreen
import com.nkoexe.text.ui.theme.TextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        window.statusBarColor = android.graphics.Color.BLACK
        window.navigationBarColor = android.graphics.Color.BLACK

        setContent {
            TextTheme {
                val viewModel: HomeViewModel = viewModel()
                val history by viewModel.history.collectAsState()
                var text by rememberSaveable { mutableStateOf("") }
                var displayedText by rememberSaveable { mutableStateOf("") }
                var isDisplaying by rememberSaveable { mutableStateOf(false) }
                val insetsController = WindowInsetsControllerCompat(window, window.decorView)

                LaunchedEffect(isDisplaying) {
                    if (isDisplaying) {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        insetsController.systemBarsBehavior =
                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                        insetsController.hide(WindowInsetsCompat.Type.systemBars())
                        window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    } else {
                        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        insetsController.show(WindowInsetsCompat.Type.systemBars())
                        window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    }
                }

                BackHandler(enabled = isDisplaying) { isDisplaying = false }

                if (isDisplaying) {
                    DisplayScreen(text = displayedText)
                } else {
                    HomeScreen(
                        text = text,
                        onTextChange = { text = it },
                        history = history,
                        onSelectHistory = { text = it },
                        onDeleteHistory = viewModel::deleteHistory,
                        onShow = {
                            if (text.isNotBlank()) {
                                displayedText = text
                                viewModel.recordDisplayed(text)
                                isDisplaying = true
                            }
                        }
                    )
                }
            }
        }
    }
}
