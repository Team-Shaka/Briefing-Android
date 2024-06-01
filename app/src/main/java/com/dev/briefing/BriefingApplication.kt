package com.dev.briefing

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BriefingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}