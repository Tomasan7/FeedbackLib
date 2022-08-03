package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.TextFeedback
import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience

open class ChatFeedback(val message: String) : TextFeedback
{
    override fun apply(audience: Audience, placeholders: Placeholders?) = audience.sendMessage(message.miniParse(placeholders))

    companion object
    {
        fun deserialize(obj: Any): ChatFeedback?
        {
            if (obj !is String)
                return null

            return ChatFeedback(obj)
        }
    }
}