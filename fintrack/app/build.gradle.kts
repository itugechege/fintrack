plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Add these for Hilt and Apollo GraphQL
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)
    kotlin("kapt") // needed for annotation processors like Room & Hilt
}
android {
    namespace = "com.example.fintrack"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.fintrack"
        minSdk = 30
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.compose.foundation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
<<<<<<< Updated upstream

    // Charts (MPAndroidChart Compose wrapper or KMP chart lib)
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Coroutines (for async data)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
=======
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation("androidx.compose.animation:animation-graphics:1.6.0") // or matching your Compose version

>>>>>>> Stashed changes
}