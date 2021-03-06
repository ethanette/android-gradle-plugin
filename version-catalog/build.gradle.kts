plugins {
    `version-catalog`
    `maven-publish`
}

group = Configs.GROUP_ID
version = Configs.VERSION

@Suppress("UnstableApiUsage")
catalog {
    versionCatalog {
        from(files("../gradle/libs.versions.toml"))
        // overwrite the "ethanetteGradle" version declared in the imported catalog
        version("ethanetteGradle", Configs.VERSION)
    }
}

publishing {
    repositories {
        publications {
            create<MavenPublication>("maven") {
                from(components["versionCatalog"])
            }
        }
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
