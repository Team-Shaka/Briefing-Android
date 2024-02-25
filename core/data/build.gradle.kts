plugins {
    alias(libs.plugins.briefing.android.library)
}

android {
    namespace = "store.newsbriefing.app.core.data"
}

dependencies {
    api(projects.core.network)
}