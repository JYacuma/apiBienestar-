plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.bienestar"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.example.bienestar"
        minSdk = 24
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
    buildFeatures {
        compose = true
    }
}

dependencies {
// Dependencias de Navigation Component
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.5")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // --------------------------------------------------------
    // 1. LIBRERÍAS CLÁSICAS PARA XML (¡Faltaban en tu proyecto!)
    // --------------------------------------------------------
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // --------------------------------------------------------
    // 2. RETROFIT Y GSON (Conexión a tu Spring Boot)
    // --------------------------------------------------------
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // --------------------------------------------------------
    // 3. ARQUITECTURA (ViewModels, LiveData, Fragments)
    // --------------------------------------------------------
    implementation("androidx.activity:activity-ktx:1.8.0")
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // --------------------------------------------------------
    // 4. LIBRERÍAS DE COMPOSE (Las que Android Studio puso por defecto)
    // --------------------------------------------------------
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)


    // --------------------------------------------------------
    // 5. TESTING
    // --------------------------------------------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

}