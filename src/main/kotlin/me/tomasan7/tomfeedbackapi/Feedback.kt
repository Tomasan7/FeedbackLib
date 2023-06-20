package me.tomasan7.tomfeedbackapi

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

interface Feedback : Serializable
{
    fun apply(audience: Audience)

    companion object
    {
        /** The [MiniMessage] instance used by feedback elements to deserialize mini-message strings. */
        var miniMessage = MiniMessage.miniMessage()

        val legacySerializer = LegacyComponentSerializer.legacy('§')
    }
}

fun Audience.apply(feedback: Feedback) = feedback.apply(this)

internal fun String.miniParse(placeholders: Placeholders? = null) =
    if (placeholders != null)
        Feedback.miniMessage.deserialize(placeholders.apply(this))
    else
        Feedback.miniMessage.deserialize(this)
