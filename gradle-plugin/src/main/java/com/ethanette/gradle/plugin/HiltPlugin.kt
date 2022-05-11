package com.ethanette.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope

class HiltPlugin : BaseConfigPlugin() {

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.dagger.hilt.android.pluginId)
    }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(project.libs.google.dagger.hiltAndroid)
        kapt(project.libs.google.dagger.hiltCompiler)

        implementation(project.libs.androidx.hilt.hiltNavigation)
        implementation(project.libs.androidx.hilt.hiltNavigationCompose)
        implementation(project.libs.androidx.hilt.hiltLifecycleViewmodel)

        androidTestImplementation(project.libs.google.dagger.hiltAndroidTesting)
        kaptAndroidTest(project.libs.google.dagger.hiltCompiler)

        testImplementation(project.libs.google.dagger.hiltAndroidTesting)
        kaptTest(project.libs.google.dagger.hiltCompiler)
    }

}