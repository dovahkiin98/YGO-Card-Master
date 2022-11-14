@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "net.inferno.ygocardmaster"
    compileSdk = 33

    signingConfigs {
        maybeCreate("testKey").apply {
            storeFile = file("../testkey.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }

    defaultConfig {
        applicationId = "net.inferno.ygocardmaster"

        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs["testKey"]

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0-alpha02"
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.activity:activity-compose:1.7.0-alpha02")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.0-alpha03")
    implementation("androidx.navigation:navigation-compose:2.6.0-alpha04")

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.foundation)
    implementation("androidx.compose.material3:material3:1.1.0-alpha02")

    implementation(libs.google.accompanist.pager)

    //region Networking
    implementation(libs.square.okhttp)
    implementation(libs.square.okhttp.logginginterceptor)

    implementation(libs.square.retrofit)
    implementation(libs.square.retrofit.moshi)

    implementation(libs.square.moshi)
    implementation(libs.square.moshi.adapters)
    implementation(libs.square.moshi.kotlin)
    kapt(libs.square.moshi.kotlin.codegen)

    implementation("io.coil-kt:coil-compose:2.2.2")
    //endregion

    //region Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    kapt(libs.androidx.hilt.compiler)
    //endregion

    //region Test
    testImplementation("junit:junit:4.13.2")

    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    //endregion
}