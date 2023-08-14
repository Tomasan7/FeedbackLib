rootProject.name = "feedbacklib"
include("feedbacklib-hoplite")

pluginManagement {
    repositories {
        // https://kotlinlang.org/docs/whatsnew19.html#configure-gradle-settings
        mavenCentral()
        gradlePluginPortal()
    }
}
