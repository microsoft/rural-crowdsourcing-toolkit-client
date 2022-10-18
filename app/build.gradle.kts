import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("kotlin-android")
    kotlin("kapt")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id(Plugin.Hilt.id) // version is picked up from parent i.e project'd root build.gradle
    id("androidx.navigation.safeargs.kotlin")
    id("com.github.ben-manes.versions") version "0.38.0"
}

android {
    compileSdk = 33
    defaultConfig {
        applicationId = "com.microsoft.research.karya"
        minSdk = 24
        targetSdk = 33
        multiDexEnabled = true
        versionCode = 25
        versionName = "1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        // Build config field to enable or disable payments
        buildConfigField("boolean", "PAYMENTS_ENABLED", "false")
    }
    buildTypes {
        named("release") {
            isMinifyEnabled = false
            setProguardFiles(
                listOf(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            )
            buildConfigField("String", "BOX_URL", "http://__deployed_box_url")
        }
        named("debug") {
            buildConfigField("String", "BOX_URL", "\"${gradleLocalProperties(project.rootDir).getProperty("LOCAL_BOX_URL")}\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
        kapt {
            arguments {
                arg("room.schemaLocation", "$projectDir/schemas")
            }
        }
    }
    lint {
        abortOnError = false
    }
    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }
    flavorDimensions("study")
    productFlavors {
        create("mit") {
            dimension = "study"
            applicationIdSuffix = ".mit2022"
        }
        create("rani") {
            dimension = "study"
            applicationIdSuffix = ".rani"
        }
        create("large") {
            dimension = "study"
            applicationIdSuffix = ".large"
        }
        create("standard") {
            dimension = "study"
            buildConfigField("boolean", "PAYMENTS_ENABLED", "true")
        }
    }
}

dependencyLocking {
    lockAllConfigurations()
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(Dependencies.AndroidX.appcompat)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.datastorePrefs)
    implementation(Dependencies.AndroidX.fragmentKtx)
    implementation(Dependencies.AndroidX.legacy_support)
    implementation(Dependencies.AndroidX.multidex)
    implementation(Dependencies.AndroidX.work_runtime)
    implementation(Dependencies.AndroidX.work_multiprocess)
    implementation(Dependencies.AndroidX.gridLayout)

    implementation(Dependencies.AndroidX.Lifecycle.common)
    implementation(Dependencies.AndroidX.Lifecycle.extensions)
    implementation(Dependencies.AndroidX.Lifecycle.livedataKtx)
    implementation(Dependencies.AndroidX.Lifecycle.runtimeKtx)
    implementation(Dependencies.AndroidX.Lifecycle.saved_state)
    implementation(Dependencies.AndroidX.Lifecycle.viewModel)
    implementation(Dependencies.AndroidX.Lifecycle.viewModelKtx)

    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)

    implementation(Dependencies.AndroidX.Room.roomKtx)
    implementation(Dependencies.AndroidX.Room.roomRuntime)
    kapt(Dependencies.AndroidX.Room.roomCompiler)

    implementation(Dependencies.AndroidX.Navigation.fragmentKtx)
    implementation(Dependencies.AndroidX.Navigation.uiKtx)

    implementation(Dependencies.AndroidX.Compose.ui)
    implementation(Dependencies.AndroidX.Compose.uiTooling)
    implementation(Dependencies.AndroidX.Compose.foundation)
    implementation(Dependencies.AndroidX.Compose.foundationLayout)
    implementation(Dependencies.AndroidX.Compose.runtime)
    implementation(Dependencies.AndroidX.Compose.runtimeLiveData)
    implementation(Dependencies.AndroidX.Compose.material3)
    implementation(Dependencies.AndroidX.Compose.material3WindowSize)

    implementation(Dependencies.Google.gson)
    implementation(Dependencies.Google.material)
    implementation(Dependencies.Google.materialComposeAdapter)

    implementation(platform(Dependencies.Google.Firebase.bom))
    implementation(Dependencies.Google.Firebase.crashlytics)
    implementation(Dependencies.Google.Firebase.analytics)

    implementation(Dependencies.Google.Hilt.hiltAndroid)
    kapt(Dependencies.Google.Hilt.hiltAndroidCompiler)

    implementation(Dependencies.Kotlin.Coroutines.core)
    implementation(Dependencies.Kotlin.Coroutines.coroutines)

    implementation(Dependencies.ThirdParty.circleImageView)
    implementation(Dependencies.ThirdParty.glide)
    implementation(Dependencies.ThirdParty.okhttp)
    implementation(Dependencies.ThirdParty.loggingInterceptor)
    implementation(Dependencies.ThirdParty.stateProgressBar)
    implementation(Dependencies.ThirdParty.Retrofit.retrofit)
    implementation(Dependencies.ThirdParty.Retrofit.gsonConverter)
    debugImplementation(Dependencies.ThirdParty.debugDB)
    implementation(Dependencies.ThirdParty.volley)
    implementation(Dependencies.ThirdParty.flowLayout)
    // Scaled dp and sp implementations
    implementation(Dependencies.ThirdParty.Intuit.ssp)
    implementation(Dependencies.ThirdParty.Intuit.sdp)
    implementation(Dependencies.ThirdParty.toggleButtonGroup) // Themed button toggle group
    implementation(Dependencies.ThirdParty.cameraView) // Camera view
    implementation(Dependencies.ThirdParty.ratingBar) // Android rating bar
    implementation(Dependencies.ThirdParty.spotlight) // Splotlight
    implementation(files("libs/zoomage-debug.aar")) // Custom aars

    // Video data collection
    implementation(Dependencies.ThirdParty.magicalExoPlayer)
    "largeImplementation" (Dependencies.Google.MLKit.faceDetectionPlayServices)
    "largeImplementation" (Dependencies.Google.MLKit.faceDetection)
    "largeImplementation" (Dependencies.ThirdParty.videoCompressor)

    // Timber for logging
    implementation(Dependencies.ThirdParty.timber)
}

kapt {
    correctErrorTypes = true
}
