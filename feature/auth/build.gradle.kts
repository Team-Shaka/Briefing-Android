plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.feature)
    alias(libs.plugins.briefing.android.library.compose)
}

android {
    namespace = "store.newsbriefing.app.feature.auth"
}

dependencies {
    api(projects.core.model)
    api(projects.core.common)
    api(projects.core.designsystem)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
}