plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.safeargs)
    id("kotlin-parcelize")
}

android {
    compileSdk = libs.versions.targetSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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

    // Lifecycle libraries
    implementation(libs.bundles.androidx.lifecycle)

    // hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Room
    implementation(libs.bundles.androidx.room)
    kapt(libs.androidx.room.compiler)

    // Navigation
    implementation(libs.bundles.navigation)

    // Lifecycle libraries
    implementation(libs.bundles.androidx.lifecycle)

    // Zxing
    implementation(libs.zxing)

    implementation(project(":utils"))
    implementation(project(":data:repository"))
    implementation(project(":model"))
    implementation(project(":feature:common"))

}