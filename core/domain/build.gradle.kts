plugins {
    alias(libs.plugins.briefing.android.library)
}

android {
    namespace = "store.newsbriefing.app.core.domain"
}

dependencies {
    api(projects.core.data)
    api(projects.core.model)
}