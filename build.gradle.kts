// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.2.1" apply false
    id("com.android.library") version "7.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.7.10" apply false
}

buildscript {
    repositories {
        google()
    }
    dependencies {
        val navVersion = "2.5.1"

        // Navigation Safe Args
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$navVersion")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}