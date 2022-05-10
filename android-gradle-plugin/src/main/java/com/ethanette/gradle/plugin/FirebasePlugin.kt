package com.ethanette.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope

class FirebasePlugin : BaseConfigPlugin() {

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.google.services.pluginId)
        apply(project.libs.plugins.firebase.perf.pluginId)
        apply(project.libs.plugins.firebase.crashlytics.pluginId)
    }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(platform(project.libs.firebase.bom))
        implementation(project.libs.firebase.messaging.ktx)
        implementation(project.libs.firebase.analytics)
        implementation(project.libs.firebase.crashlytics)
        implementation(project.libs.firebase.perf.ktx)
    }

}