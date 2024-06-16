plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.hilt)
}

android {
    namespace = "store.newsbriefing.app.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}