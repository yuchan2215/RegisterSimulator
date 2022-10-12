// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.0.0"
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    val navVersion by extra("2.5.1")

    repositories {
        google()
    }
    dependencies {
        // Navigation Safe Args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
