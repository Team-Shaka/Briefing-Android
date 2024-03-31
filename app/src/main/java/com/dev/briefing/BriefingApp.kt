package com.dev.briefing

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BriefingApp(
    appState: BriefingAppState = rememberBriefingAppState()
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = appState.snackbarHostState,
                snackbar = { data: SnackbarData ->
                    Snackbar(
                        modifier = Modifier.padding(
                            bottom = 30.dp,
                            start = 20.dp,
                            end = 20.dp
                        )
                    ) {
                        Text(text = data.visuals.message)
                    }
                }
            )
        },
        content = { innerPadding ->
            BriefingNavHost(
                modifier = Modifier.padding(innerPadding),
                appState = appState
            )
        }
    )
}