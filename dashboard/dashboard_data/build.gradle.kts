plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "ir.codroid.dashboard_data"
}



dependencies {
    implementation(project(":core"))
    implementation(project(":dashboard:dashboard_domain"))

}