plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

apply(from = "$rootDir/compose-module.gradle")

android {
    namespace = "ir.codroid.dashboard_presentation"
}



dependencies {
    implementation(project(":core_ui"))
    implementation(project(":core"))
    implementation(project(":dashboard:dashboard_domain"))

}