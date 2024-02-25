package store.newsbriefing.app.buildlogic.plugin

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import store.newsbriefing.app.buildlogic.extension.libs

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("store.newsbriefing.app.buildlogic.plugin.android.library")
                apply("store.newsbriefing.app.buildlogic.plugin.android.hilt")
            }

            dependencies {
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:designsystem"))

//                add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
//                add("implementation", libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
//                add("implementation", libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
            }
        }
    }
}