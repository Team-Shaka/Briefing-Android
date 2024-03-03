package store.newsbriefing.app.buildlogic.plugin

import com.android.build.api.dsl.ApplicationExtension
import com.google.android.libraries.mapsplatform.secrets_gradle_plugin.SecretsPluginExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import store.newsbriefing.app.buildlogic.config.Config
import store.newsbriefing.app.buildlogic.extension.configureKotlinAndroid
import store.newsbriefing.app.buildlogic.extension.configureSecret

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
            }
            // android
            extensions.configure<ApplicationExtension> {
                // default config
                defaultConfig.apply {
                    targetSdk = Config.android.targetSdkVersion
                    applicationId = Config.android.applicationId
                    versionCode = Config.android.versionCode
                    versionName = Config.android.versionName
                }
                configureKotlinAndroid(this)

                packaging {
                    resources {
                        excludes.add("/META-INF/AL2.0")
                        excludes.add("/META-INF/LGPL2.1")
                    }
                }
            }

            configureSecret()
        }
    }
}