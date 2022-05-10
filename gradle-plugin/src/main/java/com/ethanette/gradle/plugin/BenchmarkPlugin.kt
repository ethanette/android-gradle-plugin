package com.ethanette.gradle.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

class BenchmarkPlugin : BaseConfigPlugin() {

    override fun BaseExtension.configAndroid(project: Project) {
        defaultConfig {
            testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
        }

        buildTypes {
            getByName("debug") {
                isMinifyEnabled = true
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "benchmark-proguard-rules.pro"
                )
            }
            getByName("release") {
                isDefault = true
            }
        }
    }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        androidTestImplementation(project.libs.androidx.test.runner)
        androidTestImplementation(project.libs.androidx.test.ext.junit)
        androidTestImplementation(project.libs.junit)
        androidTestImplementation(project.libs.benchmark.junit4)
    }

}


