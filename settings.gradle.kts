pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots/") }
    }
}

rootProject.name = "TestGl"
include(":app")
