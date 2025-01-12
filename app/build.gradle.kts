plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.testgl"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.testgl"
        minSdk = 22
        targetSdk = 33
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
        jvmTarget = "1.8"
    }

    buildFeatures{
        compose = true
    }
    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation("androidx.compose.runtime:runtime:1.7.6")

    // Material Design 3
    //implementation ("androidx.compose.material3:material3")
    implementation ("androidx.compose.ui:ui:1.7.6")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation("androidx.compose.foundation:foundation")
    // Material Design 3
    implementation("androidx.compose.material3:material3:1.3.1")

    // Preview tooling
    debugImplementation("androidx.compose.ui:ui-tooling:1.7.6")
    implementation("androidx.compose.ui:ui-tooling:1.7.6")

    implementation("androidx.core:core-ktx:1.15.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-ktx:1.9.3")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    // Kotlin
    implementation("androidx.fragment:fragment-ktx:1.8.5")
    // LifeCycle
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")

    // Lottie animation
    implementation("com.airbnb.android:lottie-compose:6.6.2")

    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}