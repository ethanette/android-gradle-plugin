package com.ethanette.gradle.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class AndroidLibPlugin : BaseConfigPlugin() {

    override fun apply(project: Project) {
        super.apply(project)
        project.configKotlinTasks()
    }

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.android.library.pluginId)
        apply(project.libs.plugins.kotlin.android.pluginId)
        apply(project.libs.plugins.kotlin.kapt.pluginId)
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
    }

    private fun Project.configKotlinTasks() =
        tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
        }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(project.libs.kotlin.stdlib)
        implementation(project.libs.kotlinx.coroutines)

        androidTestImplementation(project.libs.androidx.test.core)
    }

}


