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
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
    implementation(libs.dokka.gradle)
}

// https://docs.gradle.org/current/userguide/publishing_gradle_plugins.html
gradlePlugin {
    plugins {
        create("android-app") {
            id = libs.plugins.ethanette.android.app.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.AndroidAppPlugin"
        }
        create("android-lib") {
            id = libs.plugins.ethanette.android.lib.get().pluginId
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
            id = libs.plugins.ethanette.kotlin.lib.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.KotlinLibPlugin"
        }
        create("publish") {
            id = libs.plugins.ethanette.publish.get().pluginId
            implementationClass = "com.ethanette.gradle.plugin.PublishPlugin"
        }
    }
}