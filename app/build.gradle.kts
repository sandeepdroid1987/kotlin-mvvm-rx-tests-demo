plugins {
  // Application Specific Plugins
  id(BuildPlugins.androidApplication)
  id(BuildPlugins.kotlinAndroid)
  id(BuildPlugins.kotlinKapt)
  id(BuildPlugins.kotlinAndroidExtensions)

  // Internal Script plugins
  id(ScriptPlugins.variants)
  id(ScriptPlugins.quality)
  id(ScriptPlugins.compilation)
}

android {
  compileSdkVersion(AndroidSdk.compile)

  defaultConfig {
    applicationId = appId
    minSdkVersion(AndroidSdk.min)
    targetSdkVersion(AndroidSdk.target)
    versionCode = 1
    versionName = "1.0"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  sourceSets {
    map { it.java.srcDir("src/${it.name}/kotlin") }

    getByName("main") { java.srcDir("$buildDir/generated/source/kapt/main") }
  }
}

dependencies {
  //Compile time dependencies
  kapt(Libraries.lifecycleCompiler)
  kapt(Libraries.daggerCompiler)
  compileOnly(Libraries.javaxAnnotation)
  compileOnly(Libraries.javaxInject)

  // Application dependencies
  implementation(Libraries.kotlinStdLib)
  implementation(Libraries.kotlinCoroutines)
  implementation(Libraries.kotlinCoroutinesAndroid)
  implementation(Libraries.appCompat)
  implementation(Libraries.ktxCore)
  implementation(Libraries.constraintLayout)
  implementation(Libraries.viewModel)
  implementation(Libraries.liveData)
  implementation(Libraries.lifecycleExtensions)
  implementation(Libraries.cardView)
  implementation(Libraries.recyclerView)
  implementation(Libraries.material)
  implementation(Libraries.androidAnnotations)
  implementation(Libraries.dagger)
  implementation(Libraries.retrofit)
  implementation(Libraries.okHttpLoggingInterceptor)

  // Unit/Android tests dependencies
  testImplementation(TestLibraries.junit4)
  testImplementation(TestLibraries.mockito)
  testImplementation(TestLibraries.kotlinMockito)
  testImplementation(TestLibraries.kluent)
  testImplementation(TestLibraries.robolectric)

  // Acceptance tests dependencies
  androidTestImplementation(TestLibraries.testRunner)
  androidTestImplementation(TestLibraries.espressoCore)
  androidTestImplementation(TestLibraries.testExtJunit)
  androidTestImplementation(TestLibraries.testRules)
  androidTestImplementation(TestLibraries.espressoIntents)

  // Development dependencies
  debugImplementation(DevLibraries.leakCanary)
  releaseImplementation(DevLibraries.leakCanaryNoop)
  testImplementation(DevLibraries.leakCanaryNoop)
}
