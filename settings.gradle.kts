pluginManagement {
    repositories {
        gradlePluginPortal()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()

    }
    plugins {
        id("com.android.application") version "7.4.0"
        id("com.android.library")     version "7.4.0"

        // Kotlin Android plugin
        id("org.jetbrains.kotlin.android") version "1.8.20"

        id("com.google.dagger.hilt.android") version "2.46.1"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LR_3"
include(":app")
 