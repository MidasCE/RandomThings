plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    id("kotlin-parcelize")
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.randomthings"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.randomthingss"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.randomthings.HiltTestRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_PICS_SUM_URL", "\"https://picsum.photos/\"")
            buildConfigField("String", "BASE_MEME_URL", "\"https://meme-api.com/\"")
            buildConfigField("String", "BASE_DAD_JOKES_URL", "\"https://icanhazdadjoke.com/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "BASE_PICS_SUM_URL", "\"https://picsum.photos/\"")
            buildConfigField("String", "BASE_MEME_URL", "\"https://meme-api.com/\"")
            buildConfigField("String", "BASE_DAD_JOKES_URL", "\"https://icanhazdadjoke.com/\"")
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
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Coroutine
    implementation(libs.coroutines.android)

    // Network
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.moshi)

    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    // Lifecycle
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.savedstate)
    implementation(libs.androidx.foundation)
    ksp(libs.lifecycle.compiler)

    // Database
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.material3.windowsize)
    implementation(libs.navigation.compose)
    implementation(libs.constraintlayout.compose)

    // JSON
    implementation(libs.moshi)
    ksp(libs.moshi.codegen)


    // Dependency Management
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.work)
    ksp(libs.hilt.compiler)

    androidTestImplementation(libs.compose.test)
    androidTestImplementation(libs.hilt.test)

    kspAndroidTest(libs.hilt.android.compiler)

    // Work
    implementation(libs.work.runtime)

    // Image
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.lottie.compose)


    // Unit Test
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    debugImplementation(libs.compose.ui.manifest)
}