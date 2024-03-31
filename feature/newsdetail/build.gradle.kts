plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.feature)
    alias(libs.plugins.briefing.android.library.compose)
}

android {
    namespace = "store.newsbriefing.app.feature.newsdetail"
}

dependencies {
    api(projects.core.model)
    api(projects.core.common)
    api(projects.core.designsystem)
}