import java.io.File
import java.io.FileInputStream
import java.util.*

private object Versions {
    val versionProperties = loadVersionProperty()

    const val appcompat = "1.5.1"
    const val fragmentKtx = "1.5.4"
    const val coreKtx = "1.9.0"
    const val annotations = "1.5.0"

    val kotlinVersion = versionProperties["kotlinVersion"]
    val androidGradlePlugin = versionProperties["gradleVersion"]
    val detekt = versionProperties["detekt"]

    const val googleMaterial = "1.7.0"
    const val systemuicontroller = "0.27.0"

    const val coroutines = "1.6.2"

    const val retrofit = "2.9.0"
    const val retrofitLogging = "4.9.1"
    const val moshi = "1.14.0"
    const val paging = "3.1.1"

    const val composeBom = "2022.10.00"
    const val composePaging = "1.0.0-alpha17"

    const val coil = "2.2.2"

    const val jodaTime = "2.12.1"
    const val jodaTimeConvert = "2.2.2"

    const val hiltAndroid = "2.44.2"
    const val hiltViewModel = "1.0.0-alpha03"
    const val javapoet = "1.13.0"

    const val jUnit = "4.13.2"
    const val mockk = "1.13.4"
    const val androidJUnit = "1.1.3"
    const val espresso = "3.3.0"
    const val mockito = "5.0.0"
    const val archTesting = "2.1.0"
    const val viewModelCompose = "2.5.1"
    const val activityCompose = "1.6.1"
}

object Android {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragmentKtx}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val materialDesign = "com.google.android.material:material:${Versions.googleMaterial}"
    const val accompanistSystemUI =
        "com.google.accompanist:accompanist-systemuicontroller:${Versions.systemuicontroller}"
    const val annotation = "androidx.annotation:annotation:${Versions.annotations}"
    const val viewModelCompose =
        "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.viewModelCompose}"
    const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
}

object BaseDependencies {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}"
    val kotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    val detektPlugin = "io.gitlab.arturbosch.detekt:detekt-gradle-plugin:${Versions.detekt}"
    val gradle = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val daggerHilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroid}"
    const val jodaTime = "joda-time:joda-time:${Versions.jodaTime}"
    const val jodaTimeConvert = "org.joda:joda-convert:${Versions.jodaTimeConvert}"
}

object Testing {
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val mockito =
        "org.mockito:mockito-core:${Versions.mockito}"
    const val extJUnit = "androidx.test.ext:junit:${Versions.androidJUnit}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val core = "androidx.arch.core:core-testing:${Versions.archTesting}"
}

object Hilt {
    const val daggerCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroid}"
    const val hiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltViewModel}"
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    const val javapoet = "com.squareup:javapoet:${Versions.javapoet}"

    const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltAndroid}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.retrofitLogging}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi}"
    const val moshiCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
}

object Coroutines {
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val testing = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
}

object Compose {
    const val composeBom = "androidx.compose:compose-bom:${Versions.composeBom}"
    const val composeMaterial = "androidx.compose.material3:material3"
    const val composeAnimation = "androidx.compose.animation:animation"
    const val composeTooling = "androidx.compose.ui:ui-tooling"
    const val composeUi = "androidx.compose.ui:ui"
    const val composeToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeFoundation = "androidx.compose.foundation:foundation"
    const val composeRuntime = "androidx.compose.runtime:runtime-livedata"
    const val composeFonts = "androidx.compose.ui:ui-text-google-fonts"
    const val composePaging = "androidx.paging:paging-compose:${Versions.composePaging}"
}

object Coil {
    const val coilCompose = "io.coil-kt:coil-compose:${Versions.coil}"
}

object Timber {
    const val timber = "com.jakewharton.timber:timber:5.0.1"
}

fun loadVersionProperty(): Properties {
    val propertiesFilePath = "buildSrc/gradle.properties"
    var propertiesFile = File(propertiesFilePath)
    if (!propertiesFile.exists()) {
        propertiesFile = File("../$propertiesFilePath")
    }

    if (!propertiesFile.exists()) {
        throw RuntimeException("Unable to find properties file: $propertiesFilePath")
    }

    val prop = Properties()
    FileInputStream(propertiesFile).use { prop.load(it) }
    return prop
}
