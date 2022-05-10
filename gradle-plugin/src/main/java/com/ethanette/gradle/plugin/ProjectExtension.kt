package com.ethanette.gradle.plugin

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.plugin.use.PluginDependency
import org.gradle.api.provider.Provider

val Project.libs get() = the<LibrariesForLibs>()

val Provider<PluginDependency>.pluginId: String
    get() = get().pluginId