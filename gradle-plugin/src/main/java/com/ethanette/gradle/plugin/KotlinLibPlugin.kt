package com.ethanette.gradle.plugin

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.dependencies

class KotlinLibPlugin : BaseConfigPlugin() {

    override fun apply(project: Project) = with(project) {
        plugins.applyPlugins(project)
        java.configJava()
        dependencies { addDependencies(project) }
    }

    private val Project.java: JavaPluginExtension
        get() = extensions.findByName("java") as? JavaPluginExtension
            ?: error("Not an Android module: $name")

    private fun JavaPluginExtension.configJava() {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.java.library.get().pluginId)
        apply(project.libs.plugins.kotlin.library.get().pluginId)
    }

}


