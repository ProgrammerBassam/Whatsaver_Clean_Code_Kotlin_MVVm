// build gradle
private const val kotlinVersion        = "1.3.50"
private const val androidGradleVersion = "3.5.0"
private const val googleServiceVersion = "4.3.0"
private const val onesignalVersion     = "[0.12.4, 0.99.99]"

//support libs
private const val appcompatVersion        = "1.0.2"
private const val vectorDrawableVerson    = "1.1.0-rc01"
private const val constraintLayoutVersion = "1.1.3"
private const val material                = "1.1.0-alpha09"
private const val perfrenceVersion        = "1.1.0-rc01"

// ads
private const val startappVersion       = "4.0.2"
private const val addcolonyVersion      = "3.3.11"
private const val addcolonyVersionMed   = "3.3.11.0"
private const val admob                 = "18.1.1"
private const val adsConstent           = "1.0.7"

// firebase
private const val analaysis = "17.1.0"
private const val message   = "20.0.0"

// dragger
private const val draggerVersion = "2.24"

// retrofit
private const val retrofitVersion = "2.6.1"
private const val okhttpVersion   = "4.1.0"
private const val okiVersion      = "2.3.0"
private const val gsonVersion     = "2.8.5"

// rx
private const val rxVersion        = "2.2.11"
private const val rxAndroidVersion = "2.1.1"

// arch
private const val lifeCycleVersion = "2.2.0-alpha03"
private const val roomVersion      = "2.2.0-alpha02"
private const val pagingVersion    = "2.1.0"

// others
private const val timberVersion               = "4.7.1"
private const val loggingInVersion            = "3.0.0"
private const val glideVersion                = "4.9.0"
private const val multiDexVersion             = "2.0.1"
private const val fragmentUtils               = "1.1.0"
private const val oneSignal                   = "[3.11.2, 3.99.99]"
private const val aboutUs                     = "1.2.1"
private const val stickSwitchVersion          = "0.0.15"
private const val rateAppVersion              = "1.0.1"
private const val tedPermissionVersion        = "2.2.2"
private const val shineButtonVersion          = "1.0.0"


//test libs
private const val junitVersion         = "4.12"
private const val runnerVersion        = "1.2.0"
private const val espressoVersion      = "3.2.0"
private const val coreTestingVersion   = "2.1.0-rc01"
private const val espressoContrVersion = "3.3.0-alpha02"

object Dependencies{

    object Android {
        val minSdkVersion     = 17
        val targetSdkVersion  = 29
        val compileSdkVersion = 29
        val applicationId     = "com.bbalabsi.whatsaver"
        val versionCode       = 2
        val versionName       = "2.0"
    }

    object Kotlin{
        val kotlin_std        = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlinVersion"
        val core              = "androidx.core:core-ktx:$appcompatVersion"
        var kotlin_reflection = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    }

    object BuildPlugins {
        const val androidGradle        = "com.android.tools.build:gradle:$androidGradleVersion"
        const val kotlinGradlePlugin   = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val googleservoceVersion = "com.google.gms:google-services:$googleServiceVersion"
        const val onesignal            = "gradle.plugin.com.onesignal:onesignal-gradle-plugin:$onesignalVersion"
    }

    object SupportLibs{
        val appcompat         = "androidx.appcompat:appcompat:$appcompatVersion"
        val vectorDrawable    = "androidx.vectfvordrawable:vectordrawable:$vectorDrawableVerson"
        val constraint_layout = "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        val design            = "com.google.android.material:material:$material"
        val perfrence         = "androidx.preference:preference-ktx:$perfrenceVersion"
    }

    object Ads {
        var startApp               = "com.startapp:inapp-sdk:$startappVersion"
        var addColonyVersion       = "com.adcolony:sdk:$addcolonyVersion"
        var addColonyMedVersion    = "com.google.ads.mediation:adcolony:$addcolonyVersionMed"
        val admobFirebase          = "com.google.firebase:firebase-ads:$admob"
        val adsConstentVersion     = "com.google.android.ads.consent:consent-library:$adsConstent"
    }


    object Firebase {
        val analysisFirebase      = "com.google.firebase:firebase-analytics:$analaysis"
        val messageFirebase       = "com.google.firebase:firebase-messaging:$message"
    }

    object Dagger {
        var main      = "com.google.dagger:dagger-android:$draggerVersion"
        var support   = "com.google.dagger:dagger-android-support:$draggerVersion"// if you use the support libraries
        var compiler  = "com.google.dagger:dagger-compiler:$draggerVersion"
        var processer = "com.google.dagger:dagger-android-processor:$draggerVersion"
    }

    object Retrofit {
        var main           = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        var gson_converter = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        var okhttp         = "com.squareup.okhttp3:okhttp:$okhttpVersion"
        var logging        = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
        val mockWebServer  = "com.squareup.okhttp3:mockwebserver:$okhttpVersion"
        var oki            = "com.squareup.okio:okio:$okiVersion"
        var gson           = "com.google.code.gson:gson:$gsonVersion"
    }

    object Rx {
        var main     = "io.reactivex.rxjava2:rxjava:$rxVersion"
        var android  = "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"
        var retrofit = "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    }

    object ArchComponents {
        val life_cycle_ext      = "androidx.lifecycle:lifecycle-extensions:$lifeCycleVersion"
        val life_cycle_compiler = "androidx.lifecycle:lifecycle-compiler:$lifeCycleVersion"
        val view_model          = "androidx.lifecycle:viewmodel:$lifeCycleVersion"
        val live_data           = "androidx.lifecycle:livedata:$lifeCycleVersion"
        var room                = "androidx.room:room-runtime:$roomVersion"
        val room_compiler       = "androidx.room:room-compiler:$roomVersion"
        val paging              = "androidx.paging:paging-runtime:$pagingVersion"
    }

    object Others {
        var timber          = "com.jakewharton.timber:timber:$timberVersion"
        var beautyLogger    = "com.github.ihsanbal:LoggingInterceptor:$loggingInVersion"
        var glide           = "com.github.bumptech.glide:glide:$glideVersion"
        var glide_processer = "com.github.bumptech.glide:compiler:$glideVersion"
        val multidex        = "androidx.multidex:multidex:$multiDexVersion"
        val fragment_utils  = "com.github.mac229.FragmentUtils:fragmentutils-kt:$fragmentUtils"
        val oneSignalLib    = "com.onesignal:OneSignal:$oneSignal"
        val aboutUsLib      = "com.github.medyo:android-about-page:$aboutUs"
        val stickSwitch     = "com.github.GwonHyeok:StickySwitch:$stickSwitchVersion"
        val rateApp         = "com.github.hotchemi:android-rate:$rateAppVersion"
        val tedPermission   = "gun0912.ted:tedpermission-rx2:$tedPermissionVersion"
        val shineButton     = "com.sackcentury:shinebutton:$shineButtonVersion"
    }

    object TestLibs{
        val junit             = "junit:junit:$junitVersion"
        val espresso          = "androidx.test.espresso:espresso-core:$espressoVersion"
        val runner            = "androidx.test:runner:$runnerVersion"
        val test_live_data    = "androidx.arch.core:core-testing:$coreTestingVersion"
        val test_room         = "androidx.room:room-testing:$roomVersion"
        val espresso_ersource = "com.android.support.test.espresso:espresso-idling-resource:$espressoVersion"
        val espresso_contrint = "androidx.test.espresso:espresso-contrib:$espressoContrVersion"
    }
}