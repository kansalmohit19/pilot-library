// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    /*alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
    alias(libs.plugins.hilt.android).apply(false)
    alias(libs.plugins.ksp).apply(false)*/

    id("com.android.library") version("8.8.1") apply false
    id("org.jetbrains.kotlin.android") version("2.0.21") apply false
    id("org.jetbrains.kotlin.jvm") version("2.0.21") apply false
    id("com.google.dagger.hilt.android") version("2.52") apply false
    id("com.google.devtools.ksp") version("2.0.21-1.0.27") apply false
}