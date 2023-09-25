import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    kotlin("kapt")
    kotlin("android")
    id("com.android.application")
    id("androidx.navigation.safeargs.kotlin")
    id("com.google.dagger.hilt.android")
}

val prod: String = gradleLocalProperties(rootDir).getProperty("production")
val staging: String = gradleLocalProperties(rootDir).getProperty("staging")

android {
    namespace = "id.novian.flowablecash"
    compileSdk = 33

    defaultConfig {
        applicationId = "id.novian.flowablecash"
        minSdk = 24
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
            buildConfigField("String", "BASE_URL", prod)
        }

        debug {
            buildConfigField("String", "BASE_URL", staging)
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
        buildConfig = true
        viewBinding = true
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {

    // Core
    implementation(Deps.Core.coreKtx)
    implementation(Deps.Core.appCompat)
    implementation(Deps.Core.navCompKtx)
    implementation(Deps.Core.coreNavComp)

    // Layout
    implementation(Deps.Core.androidMaterial)
    implementation(Deps.Core.constraintLayout)
    implementation(Deps.Core.coordinatorLayout)

    // Testing
    testImplementation(Deps.UnitTest.junit)
    androidTestImplementation(Deps.AndroidTest.junit)
    androidTestImplementation(Deps.AndroidTest.espresso)

    // Room
    implementation(Deps.Room.roomKtx)
    implementation(Deps.Room.roomRuntime)
    kapt(Deps.Room.roomCompiler)
    implementation(Deps.Room.roomRx)

    // Retrofit & OkHttp
    implementation(Deps.Retrofit.retrofit)
    implementation(Deps.Retrofit.gsonConverter)
    implementation(Deps.Retrofit.gson)
    implementation(Deps.OkHttp.okhttp)
    implementation(Deps.OkHttp.interceptor)

    // ViewModel
    implementation(Deps.ViewModel.viewModelKtx)
    implementation(Deps.ViewModel.liveData)

    // Rx
    implementation(Deps.RxJava.rxJava)
    implementation(Deps.RxJava.rxAndroid)
    implementation(Deps.RxJava.rxRetrofitAdapter)

    // Hilt
    implementation(Deps.Hilt.hilt)
    kapt(Deps.Hilt.compiler)

    // Glide
    implementation(Deps.Glide.glide)
    kapt(Deps.Glide.compiler)

    // Graph
    implementation(Deps.GraphDependency.graph)

}