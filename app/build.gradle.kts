plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    //alias(libs.plugins.android.hilt)
    //alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.example.oralang"
    compileSdk = libs.versions.compile.sdk.version.get().toInt()

    defaultConfig {
        applicationId = "com.example.oralang"
        minSdk = libs.versions.min.sdk.version.get().toInt()
        targetSdk = libs.versions.target.sdk.version.get().toInt()
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlin.compiler.extension.version.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(platform(libs.compose.bom))

    implementation(libs.bundles.andriodx)
    implementation(libs.bundles.andriodx)
    implementation(libs.bundles.compose)

    debugImplementation(libs.bundles.compose.debug)


    testImplementation(libs.junit.test.implementation)


    androidTestImplementation(libs.bundles.androidTestImplementation)
    androidTestImplementation(platform(libs.compose.bom))





    // Compose dependencies
    implementation(libs.lifecycle.viewmodel.compose)
    implementation(libs.material.icons.extended)
    implementation(libs.hilt.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)


    //Dagger - Hilt
    implementation(libs.dagger.hilt.android)

//    kapt(libs.dagger.hilt.android.compiler)
//    kapt(libs.hilt.compiler)



    //Room
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.runtime)
    ksp(libs.ksp.room.compiler)

    // Kotlin extensions and Room coroutine support
    implementation(libs.room.coroutine.support)

    //Retrofit
    implementation(libs.retrofit)


    //GSON
    implementation(libs.gson)
    implementation(libs.retrofit.converter)
}