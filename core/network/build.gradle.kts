plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.hilt)

}

android {
    namespace = "store.newsbriefing.app.core.network"
}

dependencies {
    api(projects.core.model)
    api(projects.core.common)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
}