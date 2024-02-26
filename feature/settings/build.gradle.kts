plugins {
    alias(libs.plugins.briefing.android.feature)
    alias(libs.plugins.briefing.android.library.compose)
}

android {
    namespace = "store.newsbriefing.app.feature.settings"
}

dependencies {
    implementation(projects.core.data)
}