plugins {
    `version-catalog`
    `maven-publish`
}

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
            groupId = Configs.GROUP_ID
            artifactId = Configs.Catalog.ARTIFACT_ID
            version = Configs.VERSION
        }
    }
}
