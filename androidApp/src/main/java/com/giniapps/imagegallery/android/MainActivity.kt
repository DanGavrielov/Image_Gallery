package com.giniapps.imagegallery.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.giniapps.imagegallery.data.PlaceholderDataRepository
import com.giniapps.imagegallery.user_prefs.KMMContext
import com.giniapps.imagegallery.user_prefs.UserPreferences
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

class MainActivity : AppCompatActivity() {
    private val mainScope = MainScope()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}
