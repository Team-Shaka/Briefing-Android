package store.newsbriefing.app.buildlogic.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import store.newsbriefing.app.buildlogic.config.Config
import store.newsbriefing.app.buildlogic.extension.configureKotlinAndroid
import store.newsbriefing.app.buildlogic.extension.configureSecret

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = Config.android.targetSdkVersion
            }

            configureSecret()
        }
    }
}