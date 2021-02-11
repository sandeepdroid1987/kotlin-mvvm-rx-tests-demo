const val projectName = "Sandeep MVVM Demo" //TODO: Reuse this in settings.gradle
const val appId = "com.sandeep.mvvmdemo"
const val kotlinVersion = "1.3.72"

object BuildPlugins {
    object Versions {
        const val buildToolsVersion = "4.0.0"
        const val gradleVersion = "6.5"
    }

    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "kotlin-android"
    const val kotlinKapt = "kotlin-kapt"
    const val kotlinAndroidExtensions = "kotlin-android-extensions"
}

object AndroidSdk {
    const val min = 21
    const val compile = 29
    const val target = compile
}

object Libraries {
    private object Versions {
        const val jetpack = "1.1.0"
        const val constraintLayout = "1.1.3"
        const val cardView = "1.0.0"
        const val material = "1.0.0-rc01"
        const val lifecycle = "2.2.0"
        const val lifecycleExtensions = "2.1.0"
        const val ktx = "1.3.0"
        const val coroutines = "1.3.7"
        const val retrofit = "2.3.0"
        const val okHttpLoggingInterceptor = "3.8.1"

        const val dagger = "2.11"
        const val javaxAnnotations = "1.0"
        const val javaxInject = "1"
    }

    const val kotlinStdLib             = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val kotlinCoroutines         = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val kotlinCoroutinesAndroid  = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val appCompat                = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout         = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val ktxCore                  = "androidx.core:core-ktx:${Versions.ktx}"
    const val lifecycleCompiler        = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val viewModel                = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val liveData                 = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleExtensions      = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensions}"
    const val cardView                 = "androidx.cardview:cardview:${Versions.cardView}"
    const val recyclerView             = "androidx.recyclerview:recyclerview:${Versions.jetpack}"
    const val material                 = "com.google.android.material:material:${Versions.material}"
    const val androidAnnotations       = "androidx.annotation:annotation:${Versions.jetpack}"
    const val dagger                   = "com.google.dagger:dagger:${Versions.dagger}"
    const val retrofit                 = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okHttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpLoggingInterceptor}"

    const val daggerCompiler   = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    const val javaxAnnotation  = "javax.annotation:jsr250-api:${Versions.javaxAnnotations}"
    const val javaxInject      = "javax.inject:javax.inject:${Versions.javaxInject}"
}

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13"
        const val mockito = "2.7.22"
        const val robolectric = "4.3.1"
        const val kluent = "1.14"
        const val testRunner = "1.1.0"
        const val espressoCore = "3.2.0"
        const val espressoIntents = "3.1.0"
        const val testExtensions = "1.1.1"
        const val testRules = "1.1.0"
        const val mockitoKotlinVersion = "2.1.0"
    }


    const val junit4          = "junit:junit:${Versions.junit4}"
    const val mockito         = "org.mockito:mockito-core:${Versions.mockito}"
    const val kotlinMockito    = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val robolectric     = "org.robolectric:robolectric:${Versions.robolectric}"
    const val kluent          = "org.amshove.kluent:kluent:${Versions.kluent}"
    const val testRunner      = "androidx.test:runner:${Versions.testRunner}"
    const val testRules       = "androidx.test:rules:${Versions.testRules}"
    const val espressoCore    = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val espressoIntents = "androidx.test.espresso:espresso-intents:${Versions.espressoIntents}"
    const val testExtJunit    = "androidx.test.ext:junit:${Versions.testExtensions}"
}

object DevLibraries {
    private object Versions {
        const val leakCanary = "1.5"
    }

    const val leakCanary =     "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
    const val leakCanaryNoop = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanary}"
}

object ScriptPlugins {
    const val infrastructure = "scripts.infrastructure"
    const val variants = "scripts.variants"
    const val quality = "scripts.quality"
    const val compilation = "scripts.compilation"
}