import com.rax.buildsrc.applyCommonLibConfigurations
import com.rax.buildsrc.dataDependencies
import com.rax.buildsrc.testDependencies

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

applyCommonLibConfigurations(nameSpaceValue = "com.rax.repository.search.data")
dataDependencies()
testDependencies()

dependencies {
    api(project(":repository-search:domain"))
}