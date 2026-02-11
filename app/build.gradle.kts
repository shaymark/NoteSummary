import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

apply(from = file("env.gradle.kts"))// Export the loaded env variables as a property
val envVars = extra["envVars"] as Map<String, String>

android {
    namespace = "com.markoapps.aisummerizer"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.markoapps.aisummerizer"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "OPEN_AI_API_KEY", "\"${envVars["OPEN_AI_API_KEY"] ?: "default"}\"")
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
    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.fromTarget("11")
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // Retrofit 3.0.0 is the latest stable for modern Android networking
    implementation(libs.retrofit)
    // Moshi converter for JSON serialization
    implementation(libs.converter.moshi)
    // OkHttp logging and core (Retrofit 3 uses OkHttp 5 internally)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // --- Koin Dependency Injection ---
    // Core Koin for Android
    implementation(libs.koin.android)
    // Koin support for Jetpack Compose (includes ViewModel support)
    implementation(libs.koin.androidx.compose)

    // --- Moshi (JSON Library) ---
    implementation(libs.moshi.kotlin)

    val roomVersion = "2.6.1"

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // For annotation processing (choose ONE: KSP recommended)
    ksp(libs.androidx.room.compiler)

    implementation("androidx.navigation:navigation-compose:2.9.7")
    implementation("androidx.compose.material:material-icons-extended:1.5.3")

}