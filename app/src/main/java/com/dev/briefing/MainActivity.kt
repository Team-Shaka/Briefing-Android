package com.dev.briefing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import store.newsbriefing.app.core.common.util.InAppUtil
import store.newsbriefing.app.core.designsystem.theme.BriefingTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InAppUtil.initBillingClient(this)

        setContent {
            BriefingTheme {
                BriefingApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        InAppUtil.onResume()
    }
}