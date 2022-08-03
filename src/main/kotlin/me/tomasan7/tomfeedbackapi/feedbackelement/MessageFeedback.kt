package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience

open class MessageFeedback(
    val message: String
)
{
    /**
     * Sends this [MessageFeedback] to the [player].
     */
    open fun send(player: Audience, placeholders: Placeholders? = null) = player.sendMessage(message.miniParse(placeholders))

    companion object
    {
        fun deserialize(obj: Any): MessageFeedback?
        {
            if (obj !is String)
                return null

            return MessageFeedback(obj)
        }
    }
}