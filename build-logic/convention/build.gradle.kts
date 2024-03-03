import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "store.newsbriefing.app.buildlogic"

plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.secrets.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "store.newsbriefing.app.buildlogic.plugin.android.application.compose"
            implementationClass =
                "store.newsbriefing.app.buildlogic.plugin.AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "store.newsbriefing.app.buildlogic.plugin.android.application"
            implementationClass =
                "store.newsbriefing.app.buildlogic.plugin.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "store.newsbriefing.app.buildlogic.plugin.android.library"
            implementationClass =
                "store.newsbriefing.app.buildlogic.plugin.AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "store.newsbriefing.app.buildlogic.plugin.android.library.compose"
            implementationClass =
                "store.newsbriefing.app.buildlogic.plugin.AndroidLibraryComposeConventionPlugin"
        }
        register("androidHilt") {
            id = "store.newsbriefing.app.buildlogic.plugin.android.hilt"
            implementationClass =
                "store.newsbriefing.app.buildlogic.plugin.AndroidHiltConventionPlugin"
        }
        register("jvmLibrary") {
            id = "store.newsbriefing.app.buildlogic.plugin.jvm.library"
            implementationClass =
                "store.newsbriefing.app.buildlogic.plugin.JvmLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "store.newsbriefing.app.buildlogic.plugin.android.feature"
            implementationClass =
                "store.newsbriefing.app.buildlogic.plugin.AndroidFeatureConventionPlugin"
        }
    }
}