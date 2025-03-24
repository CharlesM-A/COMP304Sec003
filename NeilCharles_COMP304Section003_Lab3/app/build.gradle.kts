plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")


}

android {
    namespace = "com.charles.neilcharles_comp304section003_lab3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.charles.neilcharles_comp304section003_lab3"
        minSdk = 29
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
        compose = true
    }
}

dependencies {

    //room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    //implementation(libs.androidx.navigation.runtime.ktx)
    ksp(libs.androidx.room.compiler) //this thing has ruined me

    //navigation
    implementation(libs.androidx.navigation.compose)

    implementation(libs.ui)
    implementation(libs.material3)
    implementation(libs.androidx.foundation)
    implementation(libs.androidx.core.ktx)
    //optional Kotlin Extensions and Coroutines
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation (libs.androidx.navigation.compose)


    configurations.all {
        resolutionStrategy {
            force("org.jetbrains:annotations:23.0.0")
        }
    }
}