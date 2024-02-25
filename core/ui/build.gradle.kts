plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.library.compose)
}

android {
    namespace = "store.newsbriefing.app.core.ui"
}

dependencies {
    api(projects.core.designsystem)
}