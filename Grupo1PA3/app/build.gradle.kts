plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.isil.android.pa3"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.isil.android.pa3"
        minSdk = 28
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    // Dependencis agregadas
    implementation(libs.androidx.core.splashscreen)   // Splash screen
    implementation(libs.androidx.navigation.compose)          // Navegación
    implementation(libs.androidx.lifecycle.viewmodel.compose) // ViewModel
    implementation(libs.androidx.compose.material.icons.core) // Íconos
    implementation(libs.retrofit)                  // Retrofit
    implementation(libs.retrofit.converter.gson)   // JSON <-> Kotlin
    implementation(libs.okhttp.logging)            // Logs HTTP
}