pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Briefing"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":core:designsystem")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:ui")
include(":core:domain")
include(":core:common")
include(":feature:home")
include(":feature:setting")
include(":feature:bookmark")
include(":feature:newsdetail")
include(":feature:auth")
include(":core:datastore")
