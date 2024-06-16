plugins {
    alias(libs.plugins.briefing.android.library)
    id("com.google.devtools.ksp")
}

android {
    namespace = "store.newsbriefing.app.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)

    implementation(libs.javax.inject)
    implementation(libs.kotlinx.coroutines.android)
}