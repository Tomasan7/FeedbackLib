package me.tomasan7.tomfeedbackapi

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.MiniMessage

interface Feedback
{
    fun apply(audience: Audience)

    companion object
    {
        /** The [MiniMessage] instance used by feedback elements to deserialize mini-message strings. */
        var miniMessage = MiniMessage.miniMessage()
    }
}

internal fun String.miniParse(placeholders: Placeholders?) =
    if (placeholders != null)
        Feedback.miniMessage.deserialize(placeholders.apply(this))
    else
        Feedback.miniMessage.deserialize(this)