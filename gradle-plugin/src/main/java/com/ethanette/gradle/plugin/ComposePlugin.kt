package com.ethanette.gradle.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope

class ComposePlugin : BaseConfigPlugin() {

    override fun BaseExtension.configAndroid(project: Project) {
        buildFeatures.compose = true

        composeOptions {
            kotlinCompilerExtensionVersion = project.libs.versions.androidXCompose.get()
        }

        packagingOptions {
            resources.excludes.apply {
                add("META-INF/AL2.0")
                add("META-INF/LGPL2.1")
            }
        }
    }

    override fun DependencyHandlerScope.addDependencies(project: Project) {
        implementation(project.libs.androidx.compose.ui.ui)
        debugImplementation(project.libs.androidx.compose.ui.uiTooling)

        implementation(project.libs.androidx.compose.ui.uiToolingPreview)
        implementation(project.libs.androidx.compose.material.material)
        implementation(project.libs.androidx.compose.material.materialIconsExtended)

        // Issue not showing compose preview
        implementation(project.libs.androidx.savedstate.savedstate)
        implementation(project.libs.androidx.appcompat.appcompat)

        implementation(project.libs.google.accompanist.accompanistInsets)
        implementation(project.libs.google.accompanist.accompanistSystemuicontroller)

        androidTestImplementation(project.libs.androidx.compose.ui.uiTest)
        androidTestImplementation(project.libs.androidx.compose.ui.uiTestJunit4)
        androidTestImplementation(project.libs.androidx.compose.ui.uiTestManifest)
    }

}


