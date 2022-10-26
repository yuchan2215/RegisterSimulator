enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "RegisterSimulator"
include(":app")
include(":utils")
include(":model")
include(":data:db")
include(":data:repository")
include(":di")
include(":feature:common")
include(":feature:setting")
