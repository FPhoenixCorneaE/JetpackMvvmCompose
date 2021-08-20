plugins {
    id("com.android.library")
    id(Plugins.commonComposeConfigs)
    `maven-publish`
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // kotlin
    addKotlinDependencies()
    // androidX
    addAndroidXDependencies()
    // lifecycle
    addLifecycleDependencies()
    // compose
    addComposeOfficialDependencies()
    // compose third party
    addComposeThirdPartyDependencies()
    // third party
    addThirdPartyDependencies()
    // FPhoenixCorneaE
    addFPhoenixCorneaEDependencies()
}

// MavenPublication 配置 start -------------------------------------------------------------
afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>(Deps.BuildType.Release) {
                from(components[Deps.BuildType.Release])
                groupId = "com.github.FPhoenixCorneaE"
                artifactId = project.name
                version = project.version.toString()
            }
        }
    }
}
// MavenPublication 配置 end ---------------------------------------------------------------
