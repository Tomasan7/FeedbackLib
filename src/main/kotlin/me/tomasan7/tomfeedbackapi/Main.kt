package me.tomasan7.tomfeedbackapi

import me.tomasan7.tomfeedbackapi.feedbackelement.MessageFeedback
import net.kyori.adventure.text.minimessage.MiniMessage

fun main()
{
    val feedbackYaml = mapOf<String, Any>(
        "message" to "<red>Welcome to the server!",
        "title" to mapOf<String, Any>(
            "title" to "<red>Welcome to the server! (title)",
            "sub-title" to "<dark_purple>We hope you'll like it here.",
            "fade-in" to 20,
            "stay" to 20,
            "fade-out" to 20
        ),
        "action-bar" to "<white>Type /help for help.",
        "sound" to mapOf<String, Any>(
            "key" to "entity.experience_orb.pickup",
            "source" to "AMBIENT",
            "volume" to 1.0,
            "pitch" to 2.0
        )
    )

    val placeholders = Placeholders.of("%GAY%", "KINGF007")
    val messageFeedback = MessageFeedback.deserialize(feedbackYaml["message"]!!, MiniMessage.miniMessage(), placeholders)

    println(messageFeedback)
}