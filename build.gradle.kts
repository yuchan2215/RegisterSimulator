import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false

    alias(libs.plugins.lint.ktlint)

    alias(libs.plugins.hilt) apply false

    alias(libs.plugins.gradle.versions)
    alias(libs.plugins.gradle.version.catalog.update)
    alias(libs.plugins.androidx.safeargs) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.dependency.graph)
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        // Navigation Safe Args
        classpath(libs.navigation.safeargs)
        classpath(libs.dependency.graph)
    }
}

fun isStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable
}

tasks.withType<DependencyUpdatesTask> {
    rejectVersionIf {
        isStable(currentVersion) && !isStable(candidate.version)
    }
}
