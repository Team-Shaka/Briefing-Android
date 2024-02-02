package store.newsbriefing.app.buildlogic.extension

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import store.newsbriefing.app.buildlogic.config.Config


internal fun Project.configureAndroidApplication(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    // android
    commonExtension.run {
        namespace = Config.android.nameSpace

        compileSdk = 34

        defaultConfig {
            minSdk = 26
            vectorDrawables.useSupportLibrary = true
        }
        compileOptions {
            sourceCompatibility = Config.jvm.javaVersion
            targetCompatibility = Config.jvm.javaVersion
        }
    }

    configureKotlin()
}

private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_18.toString()
        }
    }
}
