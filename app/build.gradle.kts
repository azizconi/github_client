plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.0.20"
}

android {
    namespace = "com.example.githubclient"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.githubclient"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // DI
    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-androidx-compose:3.5.0")

    // Ktor
    implementation("io.ktor:ktor-client-core:2.3.0")
    implementation("io.ktor:ktor-client-cio:2.3.0")
    implementation("io.ktor:ktor-client-serialization:2.3.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
    implementation("io.ktor:ktor-client-logging:2.3.0")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.0")


    implementation("androidx.room:room-runtime:2.5.1")
    ksp("androidx.room:room-compiler:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")

    val nav_version = "2.8.1"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")


    //Coil
    val coil = "2.7.0"
    implementation("io.coil-kt:coil:$coil")
    implementation("io.coil-kt:coil-compose:$coil")
    implementation("com.google.accompanist:accompanist-coil:0.13.0")

    implementation("com.google.accompanist:accompanist-permissions:0.32.0")

    // gson
    implementation("com.google.code.gson:gson:2.11.0")
}