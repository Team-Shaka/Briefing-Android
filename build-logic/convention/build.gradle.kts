import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "store.newsbriefing.app.buildlogic"

plugins {
    `kotlin-dsl`
}

//java {
//    sourceCompatibility = JavaVersion.VERSION_18
//    targetCompatibility = JavaVersion.VERSION_18
//}
//tasks.withType<KotlinCompile>().configureEach {
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_18.toString()
//    }
//}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
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
    }
}