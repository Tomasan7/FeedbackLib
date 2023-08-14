package me.tomasan7.feedbacklib.feedbackelement

import me.tomasan7.feedbacklib.PlaceholderFeedback
import me.tomasan7.feedbacklib.Placeholders
import me.tomasan7.feedbacklib.miniParse
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
