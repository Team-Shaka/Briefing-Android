plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.hilt)
}

android {
    namespace = "store.newsbriefing.app.core.data"
}

dependencies {
    api(projects.core.model)
    api(projects.core.network)
    api(projects.core.common)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.0")
}