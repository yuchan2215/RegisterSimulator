plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.androidx.safeargs)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.lint.ktlint)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
}

android {
    compileSdk = libs.versions.targetSdk.get().toInt()
    defaultConfig {
        applicationId = "xyz.miyayu.android.registersimulator"
        // Zxingが24以上を必要としている
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
    }
}

dependencies {

    implementation(libs.bundles.core)
    implementation(libs.material)

    testImplementation(libs.test)
    androidTestImplementation(libs.bundles.androidx.test)

    // Navigation
    implementation(libs.bundles.navigation)

    // Zxing
    implementation(libs.zxing)

    // Room
    implementation(libs.bundles.androidx.room)
    kapt(libs.androidx.room.compiler)

    // Lifecycle libraries
    implementation(libs.bundles.androidx.lifecycle)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(project(":data:repository"))
    implementation(project(":di"))
    implementation(project(":feature:common"))
    implementation(project(":feature:setting"))
}
