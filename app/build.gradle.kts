import java.io.FileInputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.service)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.ksp)
    alias(libs.plugins.crashlytics)
    kotlin("plugin.serialization")
}

val secrets = Properties()
secrets.load(FileInputStream("secrets.properties"))

fun getVersionCode() = if (project.hasProperty("VERSION_CODE")) project.property("VERSION_CODE").toString().toInt() else 11

fun getVersionName(): String{
    val versionName = SimpleDateFormat("yyyy/MM/dd").format(Date())
    return versionName
}

android {
    namespace = "com.wiseowl.woli"
    compileSdk = 35

    signingConfigs {
        if(project.file(secrets["KEY_STORE_FILE"].toString()).exists()) {
            maybeCreate("release").apply {
                keyAlias = secrets["KEY_STORE_ALIAS"].toString()
                keyPassword = secrets["KEY_STORE_PASSWORD"].toString()
                storeFile = project.file(secrets["KEY_STORE_FILE"].toString())
                storePassword = secrets["KEY_STORE_PASSWORD"].toString()
            }
        }
    }

    defaultConfig {
        applicationId = "com.wiseowl.woli"
        minSdk = 24
        targetSdk = 35
        versionCode = getVersionCode()
        versionName = getVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resValue("string", "GOOGLE_API_KEY", "\"${secrets["GOOGLE_API_KEY"]}\"")
        buildConfigField("String", "KEY_STORE_ALIAS", "\"${secrets["KEY_STORE_ALIAS"]}\"")
        buildConfigField("String", "PEXELS_BASE_URL", "\"${secrets["PEXELS_BASE_URL"]}\"")
        buildConfigField("String", "PEXELS_API_KEY", "\"${secrets["PEXELS_API_KEY"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.findByName("release")
        }

        debug {
            applicationIdSuffix = ".debug"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    //Test
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.runner)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    //Serializer
    implementation(libs.kotlinx.serialization.json)

    //Dependency Injection
    implementation(project.dependencies.platform(libs.koin.bom))
    implementation(libs.koin.android)
    testImplementation(libs.io.insert.koin.koin.test)
    testImplementation(libs.insert.koin.koin.test.junit4)

    //constrain layout
    implementation(libs.androidx.constraintlayout.compose)

    //Splash Screen
    implementation(libs.androidx.core.splashscreen)

    //Moshi
    implementation(libs.moshi)
    ksp(libs.moshi.kotlin.codegen)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.moshi)

    //Chucker
    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)
}