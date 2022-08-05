package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.PlaceholderFeedback
import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience

class ActionBarPlaceholderFeedback(val message: String) : PlaceholderFeedback
{
    override fun apply(audience: Audience, placeholders: Placeholders?) = audience.sendActionBar(message.miniParse(placeholders))

    override fun serialize() = message

    companion object
    {
        fun deserialize(obj: Any): ActionBarPlaceholderFeedback?
        {
            if (obj !is String)
                return null

            return ActionBarPlaceholderFeedback(obj)
        }
    }
}