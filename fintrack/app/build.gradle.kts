plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Hilt & Apollo GraphQL
    alias(libs.plugins.hilt)
    alias(libs.plugins.apollo)

    // Kotlin Annotation Processor (needed for Hilt)
    kotlin("kapt")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    packaging {
        resources.excludes.add("META-INF/*")
    }
}

dependencies {
    // ────────────────────────────────
    // Core Android + Lifecycle
    // ────────────────────────────────
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // ────────────────────────────────
    // Jetpack Compose
    // ────────────────────────────────
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation("androidx.compose.animation:animation-graphics:1.6.0")
    implementation("androidx.navigation:navigation-compose:2.8.0")


    // ────────────────────────────────
    // Kotlin Coroutines
    // ────────────────────────────────
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // ────────────────────────────────
    // Charts (MPAndroidChart)
    // ────────────────────────────────
    implementation("com.github.PhilJay:MPAndroidChart:3.1.0")

    // ────────────────────────────────
    // Dependency Injection (Hilt)
    // ────────────────────────────────
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // ────────────────────────────────
    // Apollo GraphQL
    // ────────────────────────────────
    implementation(libs.apollo.runtime)

    // ────────────────────────────────
    // Testing
    // ────────────────────────────────
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
