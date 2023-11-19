plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.chaquo.python")
}

android {
    namespace = "com.talosross.summaryyou"
    compileSdk = 34
    androidResources {
        generateLocaleConfig = true
    }
    defaultConfig {
        applicationId = "com.talosross.summaryyou"
        minSdk = 24
        targetSdk = 34
        versionCode = 20231119
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        //For Python
        ndk {
            // On Apple silicon, you can omit x86_64.
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86_64")
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    flavorDimensions += "version"

    sourceSets {
        getByName("main") {
            java.srcDirs("src/main/java")
        }

        create("paid") {
            java.srcDirs("src/main/java")
            jniLibs.srcDirs("src/paid/jni")
        }

        create("openSource") {
            java.srcDirs("src/main/java")
            jniLibs.srcDirs("src/openSource/jni")
        }
    }
    productFlavors {
        create("paid") {
            dimension = "version"
            buildConfigField("boolean", "OPEN_SOURCE", "false")
            signingConfig = signingConfigs.getByName("debug")
        }
        create("openSource") {
            dimension = "version"
            buildConfigField("boolean", "OPEN_SOURCE", "true")
        }
    }
    externalNativeBuild {
        ndkBuild {
            path = file("src/paid/jni/Android.mk")
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
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

chaquopy {
    defaultConfig {
        version = "3.8"
        pip {
            // A requirement specifier, with or without a version number:
            install("youtube-transcript-api")
            install("openai")
            install("pytube")
            install("newspaper3k")
            install("pydantic<2")
            }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.1")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3-android:1.2.0-alpha11")
    implementation("androidx.appcompat:appcompat:1.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.10.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")
    implementation("androidx.navigation:navigation-compose:2.7.5")
    implementation("com.google.code.gson:gson:2.10.1")
}