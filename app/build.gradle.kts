import org.jetbrains.kotlin.codegen.generateLanguageVersionSettingsBasedMetadataFlags
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}
apply(plugin = "dagger.hilt.android.plugin")

android {
    namespace = "com.example.lr3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.lr3"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        //dataBinding = true
        // если вы хотели DataBinding, то dataBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }


}

extensions.configure<KaptExtension> {
    // убираем падения, если в сгенерённых stub’ах временно нет нужных классов
    correctErrorTypes = true
    javacOptions {
        // максимальное число ошибок
        option("-Xmaxerrs", "500")
        // печать инфо о раундах аннот.processor’ов
        option("-XprintRounds")
        // печать, какой процессор в какой раунде
        option("-XprintProcessorInfo")
    }
}

dependencies {

    implementation(libs.hilt.android)
    kapt         (libs.hilt.android.compiler)

    // Jetpack Hilt для ViewModel/Navigation (если используете)
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    kapt         ("androidx.hilt:hilt-compiler:1.0.0")

    // Retrofit & OkHttp
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Основные AndroidX
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    implementation("androidx.activity:activity-ktx:1.6.1")
}

apply(plugin = "dagger.hilt.android.plugin")