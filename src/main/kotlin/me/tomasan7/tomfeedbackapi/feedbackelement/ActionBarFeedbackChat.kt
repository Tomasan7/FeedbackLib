package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.TextFeedback
import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience

class ActionBarFeedbackChat(val message: String) : TextFeedback
{
    override fun apply(audience: Audience, placeholders: Placeholders?) = audience.sendActionBar(message.miniParse(placeholders))

    companion object
    {
        fun deserialize(obj: Any): ActionBarFeedbackChat?
        {
            if (obj !is String)
                return null

            return ActionBarFeedbackChat(obj)
        }
    }
}