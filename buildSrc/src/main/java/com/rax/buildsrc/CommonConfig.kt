package com.rax.buildsrc

import BaseDependencies
import Configuration
import Retrofit
import com.android.build.gradle.AppExtension
import com.android.build.gradle.LibraryExtension
import com.rax.buildsrc.utils.*
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.project

fun Project.dataDependencies() {
    android {
        dependencies.apply {
            implementation(project(path = ":core"))
            networkingDependencies()
            dependencyInjection()
            implementation(BaseDependencies.jodaTime)
            implementation(BaseDependencies.jodaTimeConvert)
            implementation(Retrofit.paging)
        }
    }
}

fun Project.domainDependencies() {
    android {
        dependencies.apply {
            implementation(project(path = ":core"))
            coroutines()
            dependencyInjection()
            implementation(BaseDependencies.jodaTime)
            implementation(Retrofit.paging)
        }
    }
}

fun Project.presentationDependencies() {
    android {
        dependencies.apply {
            implementation(project(path = ":core"))
            implementation(project(path = ":shared:ui-components"))
            buildFeatures.apply {
                viewBinding = true
                compose = true
            }
            commonPresentationDependencies()
            dependencyInjection()
            composeDependencies()
            implementation(BaseDependencies.jodaTime)
        }
    }
}

fun Project.testDependencies() {
    android {
        dependencies.apply {
            testImplementation(project(path = ":test"))
            androidTestImplementation(project(path = ":android-test"))
            genericTestDependencies()
            genericAndroidTestDependencies()
        }
    }
}

fun Project.applyCommonAppConfigurations(nameSpaceValue: String) {
    android {
        require(this is AppExtension) {
            "Android application plugin has not been applied"
        }
        this.defaultConfig.applicationId = Configuration.APPLICATION_ID

        applyCommonConfigurations(nameSpaceValue = nameSpaceValue)

        buildFeatures.apply {
            viewBinding = true
            compose = true
        }
    }
}

fun Project.applyCommonLibConfigurations(nameSpaceValue: String) {
    android {
        require(this is LibraryExtension) {
            "Android library plugin has not been applied"
        }

        applyCommonConfigurations(nameSpaceValue = nameSpaceValue)

        buildFeatures.apply {
            buildConfig = true
        }

        kotlin {
            explicitApi()
        }

        packagingOptions.resources {
            //excludes += "META-INF/*.kotlin_module" // Excluding metadata will break support for Kotlin extensions
            excludes += listOf(
                "META-INF/LICENSE*",
                "META-INF/AL*",
                "META-INF/LGPL*",
                "META-INF/*.kotlin_module"
            )
        }
    }
}

private fun Project.applyCommonConfigurations(nameSpaceValue: String) {
    android {
        compileSdkVersion(Configuration.COMPILE_SDK)

        defaultConfig {
            minSdk = Configuration.MIN_SDK
            targetSdk = Configuration.TARGET_SDK

            testInstrumentationRunner = Configuration.ANDROID_TEST_INSTRUMENTATION
        }
        buildTypes {
            getByName("release") {
                isMinifyEnabled = true
                isDebuggable = false
                buildConfigField(
                    "String",
                    "BASE_URL_API",
                    "\"https://api.github.com/\""
                )
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
            getByName("debug") {
                buildConfigField(
                    "String",
                    "BASE_URL_API",
                    "\"https://api.github.com/\""
                )
                isDefault = true
                isMinifyEnabled = false
                isDebuggable = true
            }

        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
            freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
        }
        composeOptions {
            kotlinCompilerExtensionVersion = "1.3.2"
        }

        testOptions {
            unitTests.isReturnDefaultValues = true
            unitTests.isIncludeAndroidResources = true
            animationsDisabled = true
        }

        dependencies.apply {
            baseDependencies()
        }

        namespace = nameSpaceValue
    }
}
