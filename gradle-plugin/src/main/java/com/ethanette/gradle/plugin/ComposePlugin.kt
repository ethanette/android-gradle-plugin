package com.ethanette.gradle.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

class ComposePlugin : BaseConfigPlugin() {

    override fun BaseExtension.configAndroid(project: Project) {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = project.libs.versions.compose.get()
        }

        packagingOptions {
            resources.excludes.apply {
                add("META-INF/AL2.0")
                add("META-INF/LGPL2.1")
            }
        }
    }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(project.libs.compose.ui)
        debugImplementation(project.libs.compose.ui.tooling)

        implementation(project.libs.compose.ui.tooling.preview)
        implementation(project.libs.compose.material)
        implementation(project.libs.compose.material.icon)

        // Issue not showing compose preview
        implementation(project.libs.savedstate)
        implementation(project.libs.appcompat)

        implementation(project.libs.accompanist.insets)
        implementation(project.libs.accompanist.systemuicontroller)

        androidTestImplementation(project.libs.compose.ui.test)
        androidTestImplementation(project.libs.compose.ui.test.junit4)
        androidTestImplementation(project.libs.compose.ui.test.manifest)
    }

}


