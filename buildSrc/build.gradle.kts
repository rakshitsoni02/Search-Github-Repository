plugins {
    `kotlin-dsl`
}
repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation("com.android.tools.build:gradle:${project.property("gradleVersion")}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.property("kotlinVersion")}")
    implementation("com.squareup:javapoet:1.13.0")
}