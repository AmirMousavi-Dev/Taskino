plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "ir.codroid.dashboard_domain"
}



dependencies {
    implementation(project(":core"))

}