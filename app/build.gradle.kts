plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "mk.ukim.finki.my_distributor"
    compileSdk = 35

    defaultConfig {
        applicationId = "mk.ukim.finki.my_distributor"
        minSdk = 24
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit for HTTP requests
    implementation(libs.retrofit)
    // Gson converter to serialize/deserialize JSON
    implementation(libs.converter.gson)
    // Kotlin Coroutines support (for asynchronous calls)
    implementation(libs.kotlinx.coroutines.android)
    // Optional: OkHttp logging interceptor for debugging network calls
    implementation(libs.logging.interceptor)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}