# android-gradle-plugin

Android gradle version and plugins

## Version catalogs

[Shared catalogs](https://docs.gradle.org/current/userguide/platforms.html#sec:sharing-catalogs) 를 사용한 android version catalogs 입니다.

### Usage

settings.gradle.kts에 원격 저장소에 저장된 version catalogs를 생성합니다.

````
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositories {
        maven {
            setUrl("https://maven.pkg.github.com/ethanette/android-gradle-plugin")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_ACCESS_TOKEN")
            }
        }
    }

    versionCatalogs {
        create("libs") {
            from("com.github.ethanette:version-catalog:0.1.6")
        }
    }
}
````

`gradle sync` 후 다음과 같은 version catalogs에 선언된 라이브러리 사용이 가능합니다. ([version-catalog](https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog))

````
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.jetbrains.kotlinAndroid) apply false
}
````

````
dependencies {
    implementation(libs.androidx.core.coreKtx)
    implementation(libs.androidx.compose.ui.ui)
    implementation(libs.androidx.compose.material.material)
    implementation(libs.androidx.compose.ui.uiToolingPreview)
    implementation(libs.androidx.lifecycle.lifecycleRuntimeKtx)
    implementation(libs.androidx.activity.activityCompose)
    testImplementation(libs.junit.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.espresso.espressoCore)
    androidTestImplementation(libs.androidx.compose.ui.uiTestJunit4)
    androidTestImplementation(libs.androidx.compose.ui.uiTooling)
}
````

[libs.versions.toml](https://docs.gradle.org/current/userguide/platforms.html#sub:conventional-dependencies-toml) 을 사용하여 version catalogs 를 정의합니다.
> [libs.versions.toml](gradle/libs.versions.toml) 파일은 현재 정의된 버전 정보를 확인 할 수 있다.

## Gradle Plugin

[Custom Gradle Plugins](https://docs.gradle.org/current/userguide/custom_plugins.html) 를 사용한 android custom gradle plugins 입니다.
> - [AndroidAppPlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/AndroidAppPlugin.kt)
> - [AndroidLibPlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/AndroidLibPlugin.kt)
> - [ComposePlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/ComposePlugin.kt)
> - [HiltPlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/HiltPlugin.kt)
> - [FirebasePlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/FirebasePlugin.kt)
> - [BenchmarkPlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/BenchmarkPlugin.kt)
> - [KotlinLibPlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/KotlinLibPlugin.kt)
> - [PublishPlugin](gradle-plugin/src/main/java/com/ethanette/gradle/plugin/PublishPlugin.kt)

### Usage

`settings.gradle.kts`에 Gradle Plugin이 저장된 원격 저장소를 설정한다.
````
pluginManagement {
    repositories {
        maven {
            setUrl("https://maven.pkg.github.com/ethanette/android-gradle-plugin")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_ACCESS_TOKEN")
            }
        }
    }
}
````
`gradle sync` 후 프로젝트 `build.gradle.kts`에 사용하려고 하는 Gradle Plugin을 선언한다.
````
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.ethanette.androidApp) apply false
    alias(libs.plugins.ethanette.compose) apply false
}
````
`gradle sync` 후 모듈 `build.gradle.kts`에 사용하려고 하는 Gradle Plugin을 사용하여 스크립트 설정이 가능합니다.
````
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id(libs.plugins.ethanette.androidApp.get().pluginId)
    id(libs.plugins.ethanette.compose.get().pluginId)
}

android {
    defaultConfig {
        applicationId = "com.bemily.messenger"
        versionName = "1.0.0"
        versionCode = 1
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.coreKtx)
    implementation(libs.androidx.lifecycle.lifecycleRuntimeKtx)
    implementation(libs.androidx.activity.activityCompose)
}
````