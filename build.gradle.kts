// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        maven { url = uri("https://jitpack.io") }
        google()
        gradlePluginPortal()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }

    }
    dependencies {
        classpath (Dependencies.BuildPlugins.androidGradle)
        classpath (Dependencies.BuildPlugins.kotlinGradlePlugin)
        classpath (Dependencies.BuildPlugins.googleservoceVersion)
        classpath (Dependencies.BuildPlugins.onesignal)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
        mavenCentral()
        maven { url = uri("https://adcolony.bintray.com/AdColony") }
    }
}

tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}