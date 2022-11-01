private const val AGP_VERSION = "7.3.0"
private const val CRASHLYTICS_VERSION = "2.5.2"
private const val GMS_VERSION = "4.3.5"
private const val KOTLIN_VERSION = "1.4.30"
private const val LINT_VERSION = "27.3.0-alpha04"
private const val NAVIGATION_VERSION = "2.5.1"
private const val HILT_VERSION = "2.44"
private const val KT_LINT_VERSION = "11.0.0"

object Plugins {
  const val agp = "com.android.tools.build:gradle:$AGP_VERSION"
  const val agpBuilder = "com.android.tools.build:builder:$AGP_VERSION"
  const val agpBuilderModel = "com.android.tools.build:builder-model:$AGP_VERSION"
  const val agpLintModel = "com.android.tools.lint:lint-model:$LINT_VERSION"
  const val crashlytics = "com.google.firebase:firebase-crashlytics-gradle:$CRASHLYTICS_VERSION"
  const val gms = "com.google.gms:google-services:$GMS_VERSION"
  const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VERSION"
  const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VERSION"
}

sealed class Plugin(val id: String, val version: String) {
  object KotlinAndroid : Plugin("org.jetbrains.kotlin.android", "1.7.20")
  object AndroidApplication: Plugin("com.android.application", "7.3.1")
  object AndroidLibrary: Plugin("com.android.library", "7.3.1")
  object Hilt : Plugin("com.google.dagger.hilt.android", HILT_VERSION)
  object KtLint : Plugin("org.jlleitschuh.gradle.ktlint", KT_LINT_VERSION)
}

object Dependencies {

  object Kotlin {

    object Coroutines {
      private const val version = "1.4.3"

      const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    }
  }

  object AndroidX {

    private const val work_version = "2.7.0-alpha05"

    const val appcompat = "androidx.appcompat:appcompat:1.3.0-rc01"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.0-beta01"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.3"
    const val legacy_support = "androidx.legacy:legacy-support-v4:1.0.0"
    const val work_runtime = "androidx.work:work-runtime-ktx:$work_version"
    const val work_multiprocess = "androidx.work:work-multiprocess:$work_version"
    const val multidex = "androidx.multidex:multidex:2.0.1"
    const val datastorePrefs = "androidx.datastore:datastore-preferences:1.0.0-beta01"
    const val gridLayout = "androidx.gridlayout:gridlayout:1.0.0" // Grid layout for lower API levels

    object Lifecycle {
      private const val version = "2.4.0"
      private const val extension_version = "2.2.0"

      const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
      const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
      const val extensions = "androidx.lifecycle:lifecycle-extensions:$extension_version" // TODO: Replace, Deprecated
      const val viewModel = "androidx.lifecycle:lifecycle-viewmodel:$version"
      const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
      const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
      const val saved_state = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
    }

    object Room {
      private const val version = "2.5.0-alpha01"

      const val roomRuntime = "androidx.room:room-runtime:$version"
      const val roomCompiler = "androidx.room:room-compiler:$version"
      const val roomKtx = "androidx.room:room-ktx:$version"
    }

    object Navigation {

      const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
      const val uiKtx = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"
    }
  }

  object Google {

    const val gson = "com.google.code.gson:gson:2.8.6"
    const val material = "com.google.android.material:material:1.4.0-alpha02"

    object Firebase {
      const val bom = "com.google.firebase:firebase-bom:27.1.0"
      const val crashlytics = "com.google.firebase:firebase-crashlytics"
      const val analytics = "com.google.firebase:firebase-analytics"
    }

    object MLKit {
      const val faceDetection = "com.google.mlkit:face-detection:16.1.2"
      const val faceDetectionPlayServices = "com.google.android.gms:play-services-mlkit-face-detection:16.2.0"
    }

    object Hilt {
      const val hiltAndroid = "com.google.dagger:hilt-android:$HILT_VERSION"
      const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:$HILT_VERSION"
    }

  }

  object ThirdParty {

    const val circleImageView = "de.hdodenhof:circleimageview:3.1.0"
    const val debugDB = "com.amitshekhar.android:debug-db:1.0.6"
    const val glide = "com.github.bumptech.glide:glide:4.12.0"
    const val stateProgressBar = "com.kofigyan.stateprogressbar:stateprogressbar:1.0.0"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.1"
    const val okhttp = "com.squareup.okhttp3:okhttp:5.0.0-alpha.2"
    const val cameraView = "com.otaliastudios:cameraview:2.7.2"
    const val ratingBar = "me.zhanghai.android.materialratingbar:library:1.3.1"
    const val spotlight = "com.github.takusemba:spotlight:2.0.5"
    const val toggleButtonGroup = "nl.bryanderidder:themed-toggle-button-group:1.3.4"
    const val flowLayout = "com.nex3z:flow-layout:1.3.4-beta01"
    const val volley = "com.mcxiaoke.volley:library:1.0.19" // why this one? This is Deprecated
    const val magicalExoPlayer = "com.github.HamidrezaAmz:MagicalExoPlayer:2.0.6"
    const val videoCompressor = "com.github.fishwjy:VideoCompressor:master-SNAPSHOT"

    object Retrofit {
      private const val version = "2.9.0"

      const val retrofit = "com.squareup.retrofit2:retrofit:$version"
      const val gsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    }

    object Intuit {
      private const val version = "1.0.6"

      const val ssp = "com.intuit.ssp:ssp-android:$version"
      const val sdp = "com.intuit.sdp:sdp-android:$version"
    }
  }
}
