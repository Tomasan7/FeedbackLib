package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Placeholders
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

open class MessageFeedback(
    val message: String,
    private val miniMessage: MiniMessage = MiniMessage.miniMessage()
)
{
    /**
     * Creates a [Component] from this message.
     * @param placeholders Placeholders to replace.
     */
    fun build(placeholders: Placeholders? = null): Component
    {
        return if (placeholders != null) miniMessage.deserialize(placeholders.apply(message))
        else miniMessage.deserialize(message)
    }

    /**
     * Sends this [MessageFeedback] to the [player].
     */
    open fun send(player: Audience, placeholders: Placeholders? = null) = player.sendMessage(build(placeholders))

    companion object
    {
        fun deserialize(obj: Any, miniMessage: MiniMessage? = null): MessageFeedback?
        {
            if (obj !is String)
                return null

            return if (miniMessage != null)
                MessageFeedback(obj, miniMessage)
            else
                MessageFeedback(obj)
        }
    }
}