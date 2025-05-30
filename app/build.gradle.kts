plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt.android)
}

android {
    namespace = "com.mauro.composehoroscopo"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.mauro.composehoroscopo"
        minSdk = 24
        targetSdk = 35
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
        kotlinCompilerExtensionVersion = "1.5.11"

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {



    // Navigation for Jetpack Compose
    implementation(libs.androidx.navigation.compose)

// Hilt integration with Navigation in Compose
    implementation(libs.androidx.hilt.navigation.compose)
    //Retrofit si
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.logging.interceptor)

    //Camera X si
    implementation (libs.androidx.camera.core)
    implementation (libs.androidx.camera.camera2)
    implementation (libs.androidx.camera.lifecycle)
    implementation (libs.androidx.camera.view)
    implementation (libs.androidx.camera.extensions)

    //UnitTesting
    testImplementation (libs.kotlintest.runner.junit5)
    testImplementation (libs.mockk)

    //en vez de espreso




    androidTestImplementation (libs.hilt.android.testing)



    kaptAndroidTest(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    kapt (libs.hilt.android.compiler)

    implementation (libs.androidx.material.icons.extended)





    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}