plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}
android {
    namespace = "com.example.windowsmobilerunner"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.windowsmobilerunner"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "0.1-prototype"
    }
}
