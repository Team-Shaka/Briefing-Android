plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.hilt)

}

android {
    namespace = "store.newsbriefing.app.core.network"
}

dependencies {
    implementation(projects.core.datastore)
    api(projects.core.model)
    api(projects.core.common)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp3.okhttp)
    implementation(libs.okhttp3.logging.interceptor)
}