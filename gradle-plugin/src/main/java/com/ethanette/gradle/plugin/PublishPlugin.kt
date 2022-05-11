package com.ethanette.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.gradle.DokkaTask

class PublishPlugin : BaseConfigPlugin() {

    override fun apply(project: Project) {
        super.apply(project)

        val pluginExtension =
            project.extensions.create<PublishPluginExtension>("publishConfig")

        configurePublish(project, pluginExtension)
        configureDokka(project)
    }

    override fun PluginContainer.applyPlugins(project: Project) {
        apply(project.libs.plugins.mavenPublish.pluginId)
        apply(project.libs.plugins.jetbrains.dokka.pluginId)
    }

    private fun configureDokka(project: Project) {
        project.afterEvaluate {
            tasks.withType<DokkaTask>().configureEach {
                dokkaSourceSets.named("main") {
                    moduleName.set(project.name)
                    includes.from("Module.md")
                }
                outputDirectory.set(file("${buildDir}/html"))
            }
        }
    }

    private fun configurePublish(project: Project, pluginExtension: PublishPluginExtension) {
        project.afterEvaluate {
            configure<PublishingExtension> {
                publications {
                    create<MavenPublication>("release") {
                        if (plugins.hasPlugin("com.android.library")) {
                            from(components.getByName("release"))
                        } else {
                            from(components.getByName("java"))
                        }
                        groupId = pluginExtension.groupId!!
                        artifactId = pluginExtension.artifactId!!
                        version = pluginExtension.version!!
                    }
                }
                repositories {
                    maven {
                        isAllowInsecureProtocol = true
                        val releasesRepoUrl = findProperty("sonattype.releases").toString()
                        val snapshotsRepoUrl = findProperty("sonattype.snapshot").toString()
                        setUrl(
                            if (pluginExtension.version!!.endsWith("-SNAPSHOT")
                            ) snapshotsRepoUrl else releasesRepoUrl
                        )
                        credentials {
                            username = findProperty("sonattype.username").toString()
                            password = findProperty("sonattype.password").toString()
                        }
                    }
                }
            }
        }
    }

}

