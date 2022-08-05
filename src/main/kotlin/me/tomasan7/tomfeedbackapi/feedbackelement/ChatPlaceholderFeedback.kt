package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.PlaceholderFeedback
import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience

open class ChatPlaceholderFeedback(val message: String) : PlaceholderFeedback
{
    override fun apply(audience: Audience, placeholders: Placeholders?) = audience.sendMessage(message.miniParse(placeholders))

    override fun serialize() = message

    companion object
    {
        fun deserialize(obj: Any): ChatPlaceholderFeedback?
        {
            if (obj !is String)
                return null

            return ChatPlaceholderFeedback(obj)
        }
    }
}