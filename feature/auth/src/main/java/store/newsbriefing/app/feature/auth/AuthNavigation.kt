package store.newsbriefing.app.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import store.newsbriefing.app.feature.auth.signIn.SignInRoute

const val signInRoute = "signIn_route"

fun NavController.navigateToSignIn() {
    navigate(signInRoute)
}

fun NavGraphBuilder.signInScreen() {
    composable(
        route = signInRoute
    ) {
        SignInRoute()
    }
}