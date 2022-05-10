plugins {
    `version-catalog`
    `maven-publish`
}

group = Configs.GROUP_ID
version = Configs.VERSION

catalog {
    versionCatalog {
        from(files("../gradle/libs.versions.toml"))
        // overwrite the "ethanette-gradle" version declared in the imported catalog
        version("ethanette-gradle", Configs.VERSION)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
        }
    }
}
