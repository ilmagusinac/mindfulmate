plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    kotlin("plugin.serialization") version "2.0.0"
}

android {
    namespace = "com.example.mindfulmate"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mindfulmate"
        minSdk = 26 //24
        targetSdk = 34
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation (libs.androidx.foundation.layout)

    //SplashScreen
    implementation (libs.androidx.core.splashscreen)

    //Navigation
    implementation(libs.androidx.navigation.compose)
    implementation (libs.androidx.material.icons.extended)

    //ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx.v261)

    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.androidx.ui.test.android)
    kapt(libs.hilt.android.compiler)
    //implementation(libs.androidx.hilt.lifecycle.viewmodel)
    kapt(libs.androidx.hilt.compiler)
    implementation (libs.androidx.hilt.navigation.fragment)
    implementation(libs.androidx.hilt.navigation.compose)

    //Firebase
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore")
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-storage")

    //Authentication with Credential Manager
    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    //GenerativeAI
    implementation("com.google.ai.client.generativeai:generativeai:0.7.0")

    //Retrofit and OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.3")

    //Coil
    //implementation(libs.coil.compose)
   // implementation(libs.coil.network)
    implementation("io.coil-kt.coil3:coil-compose:3.0.4")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.4")


    //WorkManager
    implementation("androidx.work:work-runtime-ktx:2.8.0")

    //DataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    //Y-Charts
    implementation ("co.yml:ycharts:2.1.0")

    // JUnit for unit testing
    testImplementation ("junit:junit:4.13.2")

    // MockK for mocking dependencies
    testImplementation ("io.mockk:mockk:1.13.5")

    // Kotlin Coroutines Test for coroutine-based testing
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.2")

    // Truth for readable assertions
    testImplementation ("com.google.truth:truth:1.1.3")

    // Mock Web Server for API testing
    testImplementation ("com.squareup.okhttp3:mockwebserver:4.9.3")

    testImplementation ("org.robolectric:robolectric:4.10")

   // androidTestImplementation (libs.androidx.core)



    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}