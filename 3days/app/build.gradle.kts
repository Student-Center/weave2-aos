import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    id("com.google.gms.google-services")
}

val properties = gradleLocalProperties(rootDir, providers)

android {
    namespace = "com.weave.a3days"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.weave.a3days"
        minSdk = 26
        targetSdk = 34
        versionCode = 1

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        versionName = "0.0.1"
        versionCode = if (project.hasProperty("versionCode")) {
            project.property("versionCode").toString().toInt()
        } else {
            1
        }
    }

    applicationVariants.all {
        outputs.all {
            val outputImpl = this as com.android.build.gradle.internal.api.ApkVariantOutputImpl
            val newFileName = "3days-${name}.apk"
            outputImpl.outputFileName = newFileName
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = properties["SIGNED_KEY_ALIAS"] as String?
            keyPassword = properties["SIGNED_KEY_PASSWORD"] as String?
            storeFile = properties["SIGNED_STORE_FILE"]?.let { file(it) }
            storePassword = properties["SIGNED_STORE_PASSWORD"] as String?
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.bundles.compose)
    debugImplementation(libs.bundles.compose.debug)
    androidTestImplementation(libs.bundles.android.test)
    testImplementation(libs.junit)

    implementation(libs.hilt.android)
    implementation(libs.hilt.compose.navigation)
    ksp(libs.hilt.android.compiler)

    implementation(project(":core:utils"))
    implementation(project(":core:design-system"))
    implementation(project(":data"))

    implementation(project(":feat:intro"))
    implementation(project(":feat:my-profile"))
}