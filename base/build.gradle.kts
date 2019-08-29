plugins{
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion (Dependencies.Android.compileSdkVersion)


    defaultConfig {
        minSdkVersion    (Dependencies.Android.minSdkVersion)
        targetSdkVersion (Dependencies.Android.targetSdkVersion)
        versionCode               = 1
        versionName               = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false



            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    kapt {
        generateStubs = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation (Dependencies.Kotlin.kotlin_std)
    implementation (Dependencies.Kotlin.core)

    implementation (Dependencies.SupportLibs.appcompat)
    implementation (Dependencies.SupportLibs.constraint_layout)
    implementation (Dependencies.SupportLibs.design)

    implementation (Dependencies.Others.timber)
    implementation (Dependencies.Others.glide)
    kapt           (Dependencies.Others.glide_processer)
    implementation (Dependencies.Others.beautyLogger) {
        exclude(group = "org.json", module = "json")
    }

    implementation (Dependencies.Dagger.main)
    implementation (Dependencies.Dagger.support)
    kapt           (Dependencies.Dagger.processer)
    kapt           (Dependencies.Dagger.compiler)

    implementation (Dependencies.Retrofit.main)
    implementation (Dependencies.Retrofit.okhttp)
    implementation (Dependencies.Retrofit.oki)
    implementation (Dependencies.Retrofit.logging)
    implementation (Dependencies.Retrofit.gson)
    implementation (Dependencies.Retrofit.gson_converter)
    implementation (Dependencies.Retrofit.mockWebServer)

    implementation (Dependencies.Rx.main)
    implementation (Dependencies.Rx.android)
    implementation (Dependencies.Rx.retrofit)

    implementation  (Dependencies.ArchComponents.room)
    kapt            (Dependencies.ArchComponents.room_compiler)
    implementation  (Dependencies.ArchComponents.life_cycle_ext)
    kapt            (Dependencies.ArchComponents.life_cycle_compiler)
    implementation  (Dependencies.ArchComponents.paging)

    testImplementation        (Dependencies.TestLibs.junit)
    androidTestImplementation (Dependencies.TestLibs.runner)
    androidTestImplementation (Dependencies.TestLibs.espresso)

    testImplementation (Dependencies.TestLibs.test_live_data)
    testImplementation (Dependencies.TestLibs.test_room)

    androidTestImplementation (Dependencies.TestLibs.espresso_ersource)
    androidTestImplementation (Dependencies.TestLibs.espresso_contrint)
}
