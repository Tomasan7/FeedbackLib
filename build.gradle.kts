plugins {
    kotlin("jvm") version "1.8.20"
    id("org.jetbrains.dokka") version "1.8.20"
    `java-library`
    `maven-publish`
}

group = "me.tomasan7"
version = "1.2.0"

val adventureVersion = "4.14.0"

allprojects {
    group = rootProject.group
    version = rootProject.version

    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "java-library")
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation(kotlin("stdlib"))

        api("net.kyori:adventure-api:$adventureVersion")
    }

    kotlin {
        jvmToolchain(17)
    }

    java {
        withSourcesJar()
    }

    tasks.compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    // https://github.com/Kotlin/dokka/blob/master/examples/gradle/dokka-library-publishing-example/build.gradle.kts

    val dokkaJavadocJar by tasks.register<Jar>("dokkaJavadocJar") {
        dependsOn(tasks.dokkaJavadoc)
        from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
        archiveClassifier.set("javadoc")
    }

    val dokkaHtmlJar by tasks.register<Jar>("dokkaHtmlJar") {
        dependsOn(tasks.dokkaHtml)
        from(tasks.dokkaHtml.flatMap { it.outputDirectory })
        archiveClassifier.set("html-doc")
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(components["java"])
                artifact(dokkaJavadocJar)
                artifact(dokkaHtmlJar)
            }
        }
    }
}

dependencies {
    api("net.kyori:adventure-text-minimessage:$adventureVersion")
    implementation("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
}

tasks.wrapper {
    distributionType = Wrapper.DistributionType.ALL
}
