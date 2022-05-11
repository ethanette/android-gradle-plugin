package com.ethanette.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.kotlin.dsl.DependencyHandlerScope

class FirebasePlugin : BaseConfigPlugin() {

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.google.gms.googleServices.pluginId)
        apply(project.libs.plugins.google.firebase.firebasePerf.pluginId)
        apply(project.libs.plugins.google.firebase.crashlytics.pluginId)
    }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(platform(project.libs.google.firebase.firebaseBom))
        implementation(project.libs.google.firebase.firebaseMessagingKtx)
        implementation(project.libs.google.firebase.firebaseAnalyticsKtx)
        implementation(project.libs.google.firebase.firebaseCrashlyticsKtx)
        implementation(project.libs.google.firebase.firebasePerfKtx)
    }

}