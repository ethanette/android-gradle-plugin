package com.ethanette.gradle.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

abstract class BaseConfigPlugin : Plugin<Project> {

    private val Project.android: BaseExtension
        get() = extensions.findByName("android") as? BaseExtension
            ?: error("Not an Android module: $name")

    override fun apply(project: Project) = with(project) {
        plugins.applyPlugins(project)
        android.configAndroid(project)
        dependencies { addDependencies(project) }
    }

    protected open fun PluginContainer.applyPlugins(project: Project) {}

    protected open fun BaseExtension.configAndroid(project: Project) {}

    protected open fun DependencyHandlerScope.addDependencies(project: Project) {}

}

fun DependencyHandler.kapt(dependency: Any) {
    add("kapt", dependency)
}

fun DependencyHandler.implementation(dependency: Any) {
    add("implementation", dependency)
}

fun DependencyHandler.debugImplementation(dependency: Any) {
    add("debugImplementation", dependency)
}

fun DependencyHandler.api(dependency: Any) {
    add("api", dependency)
}

fun DependencyHandler.androidTestImplementation(dependency: Any) {
    add("androidTestImplementation", dependency)
}

fun DependencyHandler.kaptAndroidTest(dependency: Any) {
    add("kaptAndroidTest", dependency)
}

fun DependencyHandler.testImplementation(dependency: Any) {
    add("testImplementation", dependency)
}

fun DependencyHandler.kaptTest(dependency: Any) {
    add("kaptTest", dependency)
}

fun DependencyHandler.coreLibraryDesugaring(dependency: Any) {
    add("coreLibraryDesugaring", dependency)
}

fun DependencyHandler.implementation(
    project: Project,
    localDependency: String,
    dependency: String,
) {
    implementation(
        when {
            project.subprojects.any { it.path == localDependency } -> project(localDependency)
            dependency.startsWith(":") -> project(dependency)
            else -> dependency
        }
    )
}

fun DependencyHandler.api(
    project: Project,
    localDependency: String,
    dependency: String
) {
    api(
        when {
            project.subprojects.any { it.path == localDependency } -> project(localDependency)
            dependency.startsWith(":") -> project(dependency)
            else -> dependency
        }
    )
}
