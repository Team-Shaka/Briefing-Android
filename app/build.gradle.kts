import store.newsbriefing.app.buildlogic.config.Config

plugins {
    alias(libs.plugins.briefing.android.application)
    alias(libs.plugins.briefing.android.application.compose)
}

android {
    namespace = Config.android.nameSpace
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.common)
    implementation(projects.feature.home)
    implementation(projects.feature.bookmark)
    implementation(projects.feature.newsdetail)
    implementation(projects.feature.setting)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling.preview)
}