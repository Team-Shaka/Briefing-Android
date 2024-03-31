package com.dev.briefing

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun rememberBriefingAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope()
): BriefingAppState {
    return remember {
        BriefingAppState(
            navController,
            snackbarHostState,
            scope
        )
    }
}

@Stable
class BriefingAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    val scope: CoroutineScope
) {
    fun showSnackBar(message: String) {
        scope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }
}