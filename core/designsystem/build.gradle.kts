plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.library.compose)
}

android {
    namespace = "store.newsbriefing.app.core.designsystem"
}

dependencies {
    api(libs.androidx.activity.compose)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.ui.tooling.preview)
}