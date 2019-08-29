plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    "com.onesignal.androidsdk.onesignal-gradle-plugin*"
}

android {
    compileSdkVersion(Dependencies.Android.compileSdkVersion)
    defaultConfig {
        applicationId = Dependencies.Android.applicationId

        manifestPlaceholders = mapOf("onesignal_app_id" to "85beb108-6cbe-4157-9abe-748f3bb5119e",
                "onesignal_google_project_number" to "924037774862")

        minSdkVersion(Dependencies.Android.minSdkVersion)
        targetSdkVersion(Dependencies.Android.targetSdkVersion)
        multiDexEnabled                   = true
        versionCode                       = Dependencies.Android.versionCode
        versionName                       = Dependencies.Android.versionName
        testInstrumentationRunner         = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }
    buildTypes {
        getByName("release") {

            isShrinkResources = false

            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    kapt {
        generateStubs = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(project(":base"))

    implementation(Dependencies.Kotlin.kotlin_std)
    implementation(Dependencies.Kotlin.core)
    implementation(Dependencies.Kotlin.kotlin_reflection)

    implementation(Dependencies.SupportLibs.appcompat)
    implementation(Dependencies.SupportLibs.constraint_layout)
    implementation(Dependencies.SupportLibs.design)
    implementation(Dependencies.SupportLibs.perfrence)

    implementation(Dependencies.Others.timber)
    implementation(Dependencies.Others.multidex)
    implementation(Dependencies.Others.fragment_utils)
    implementation(Dependencies.Others.oneSignalLib)
    implementation(Dependencies.Others.aboutUsLib)
    implementation(Dependencies.Others.stickSwitch)
    implementation(Dependencies.Others.rateApp)
    implementation(Dependencies.Others.tedPermission)
    implementation(Dependencies.Others.shineButton)

    implementation(Dependencies.Ads.startApp)
    implementation(Dependencies.Ads.addColonyVersion)
    implementation(Dependencies.Ads.addColonyMedVersion)
    implementation(Dependencies.Ads.admobFirebase)
    implementation(Dependencies.Ads.adsConstentVersion)

    implementation(Dependencies.ArchComponents.room)
    kapt(Dependencies.ArchComponents.room_compiler)
    implementation(Dependencies.ArchComponents.life_cycle_ext)
    kapt(Dependencies.ArchComponents.life_cycle_compiler)


    implementation(Dependencies.Retrofit.gson)
    implementation(Dependencies.Retrofit.gson_converter)

    implementation(Dependencies.Dagger.main)
    implementation(Dependencies.Dagger.support)
    kapt(Dependencies.Dagger.processer)
    kapt(Dependencies.Dagger.compiler)

    implementation(Dependencies.Firebase.analysisFirebase)
    implementation(Dependencies.Firebase.messageFirebase)

    testImplementation(Dependencies.TestLibs.junit)
    androidTestImplementation(Dependencies.TestLibs.runner)
    androidTestImplementation(Dependencies.TestLibs.espresso)

    testImplementation(Dependencies.TestLibs.test_live_data)
    testImplementation(Dependencies.TestLibs.test_room)

    androidTestImplementation(Dependencies.TestLibs.espresso_ersource)
    androidTestImplementation(Dependencies.TestLibs.espresso_contrint)
}

apply(mapOf("plugin" to "com.google.gms.google-services"))
