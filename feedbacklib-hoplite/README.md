# Hoplite module

This module contains implementations of [Hoplite's](https://github.com/sksamuel/hoplite) [Decoder](https://github.com/sksamuel/hoplite#decoders).

## Example Usage

```kotlin
data class Config(
    val countdownMsgs: Map<Int, Feedback>,
    val joinMsg: PlaceholderFeedback,
    val leaveMsg: PlaceholderFeedback,
    val winMsg: PlaceholderFeedback,
    val loseMsg: PlaceholderFeedback,
    val spectateMsg: Feedback
)

val config = ConfigLoaderBuilder.default()
            .addPreprocessor(AmpersandPreprocessor)
            .addDecoder(FeedbackDecoder)
            .addDecoder(PlaceholderFeedbackDecoder)
            .addDecoder(ComponentDecoder)
            .addFileSource("config.yml")
            .build()
            .loadConfigOrThrow<Config>()

player.apply(config.joinMsg)
```

## Installation

Don't also forget to include your format module. (yaml, json, etc.)
https://github.com/sksamuel/hoplite#supported-formats

###### Maven

```xml
<repositories>
    ...
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    ...
    <dependency>
        <groupId>com.github.Tomasan7.FeedbackLib</groupId>
        <artifactId>feedbacklib-hoplite</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

###### Gradle (Groovy)

```groovy
repositories {
    ...
    maven { url 'https://jitpack.io' }
}

dependencies {
    ...
    implementation 'com.github.Tomasan7.FeedbackLib:feedbacklib-hoplite:<VERSION>'
}
```

###### Gradle (Kotlin)

```kotlin
repositories {
    ...
    maven("https://jitpack.io")
}

dependencies {
    ...
    implementation("com.github.Tomasan7.FeedbackLib:feedbacklib-hoplite:<VERSION>")
}
```
