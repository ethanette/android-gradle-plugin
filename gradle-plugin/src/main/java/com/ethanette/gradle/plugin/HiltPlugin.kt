package com.ethanette.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope

class HiltPlugin : BaseConfigPlugin() {

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.hilt.pluginId)
    }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(project.libs.hilt.android)
        kapt(project.libs.hilt.compiler)

        implementation(project.libs.hilt.navigation)
        implementation(project.libs.hilt.navigation.compose)
        implementation(project.libs.hilt.lifecycle.viewmodel)

        androidTestImplementation(project.libs.hilt.android.testing)
        kaptAndroidTest(project.libs.hilt.android.compiler)

        testImplementation(project.libs.hilt.android.testing)
        kaptTest(project.libs.hilt.android.compiler)
    }

}