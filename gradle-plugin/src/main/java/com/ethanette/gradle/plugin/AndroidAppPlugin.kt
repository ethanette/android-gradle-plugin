package com.ethanette.gradle.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidAppPlugin : BaseConfigPlugin() {

    override fun apply(project: Project) {
        super.apply(project)
        project.configKotlinTasks()
    }

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.android.application.pluginId)
        apply(project.libs.plugins.jetbrains.kotlinAndroid.pluginId)
        apply(project.libs.plugins.kotlinKapt.pluginId)
    }

    override fun BaseExtension.configAndroid(project: Project) {
        compileSdkVersion(Configs.Android.COMPILE_SDK)

        defaultConfig {
            minSdk = Configs.Android.MIN_SDK
            targetSdk = Configs.Android.TARGET_SDK

            vectorDrawables {
                useSupportLibrary = true
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        testOptions {
            // Options for controlling unit tests execution.(https://developer.android.com/training/testing/unit-testing/local-unit-tests?hl=ko#include-framework-dependencies)
            unitTests.isIncludeAndroidResources = true
            // Error: "Method ... not mocked"(https://developer.android.com/training/testing/unit-testing/local-unit-tests?hl=ko#error-not-mocked)
            unitTests.isReturnDefaultValues = true
        }
    }

    private fun Project.configKotlinTasks() =
        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(project.libs.jetbrains.kotlin.kotlinStdlibJdk8)
        implementation(project.libs.jetbrains.kotlinx.kotlinxCoroutinesCore)

        testImplementation(project.libs.junit.junit)
        testImplementation(project.libs.google.truth.truth)

        androidTestImplementation(project.libs.androidx.test.ext.junit)
        androidTestImplementation(project.libs.androidx.test.espresso.espressoCore)
    }

}
