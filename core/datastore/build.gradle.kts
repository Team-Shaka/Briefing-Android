plugins {
    alias(libs.plugins.briefing.android.library)
    alias(libs.plugins.briefing.android.hilt)
}

android {
    namespace = "store.newsbriefing.app.core.datastore"
}

dependencies {
    api(projects.core.model)
    api(projects.core.network)
    api(projects.core.common)
    api(projects.core.datastore)

    implementation(libs.datastore.preferences)
}