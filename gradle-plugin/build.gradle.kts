plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
}

group = Configs.GROUP_ID
version = Configs.VERSION

dependencies {
    /**
     * Workaround to make version catalogs accessible from precompiled script plugins
     * https://github.com/gradle/gradle/issues/15383
     */
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.android.tools.build.gradle)
    implementation(libs.jetbrains.kotlin.kotlinGradle)
    implementation(libs.jetbrains.dokka.dokkaGradle)
}

// https://docs.gradle.org/current/userguide/publishing_gradle_plugins.html
gradlePlugin {
    plugins {
        create("android-app") {
            id = libs.plugins.ethanette.androidApp.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.AndroidAppPlugin"
        }
        create("android-lib") {
            id = libs.plugins.ethanette.androidLib.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.AndroidLibPlugin"
        }
        create("benchmark") {
            id = libs.plugins.ethanette.benchmark.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.BenchmarkPlugin"
        }
        create("compose") {
            id = libs.plugins.ethanette.compose.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.ComposePlugin"
        }
        create("firebase") {
            id = libs.plugins.ethanette.firebase.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.FirebasePlugin"
        }
        create("hilt") {
            id = libs.plugins.ethanette.hilt.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.HiltPlugin"
        }
        create("kotlin-lib") {
            id = libs.plugins.ethanette.kotlinLib.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.KotlinLibPlugin"
        }
        create("publish") {
            id = libs.plugins.ethanette.publish.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.PublishPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GithubPackages"
            setUrl("https://maven.pkg.github.com/ethanette/${rootProject.name}")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_ACCESS_TOKEN")
            }
        }
    }
}