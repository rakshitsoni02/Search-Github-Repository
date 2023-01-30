import com.rax.buildsrc.applyCommonLibConfigurations
import com.rax.buildsrc.presentationDependencies
import com.rax.buildsrc.testDependencies

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

applyCommonLibConfigurations(nameSpaceValue = "com.rax.repository.search.presentation")
presentationDependencies()
testDependencies()

dependencies {
    implementation(project(":repository-search:data"))
}
