package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Feedback
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component

class ActionBarFeedback(val message: Component) : Feedback
{
    override fun apply(audience: Audience) = audience.sendActionBar(message)

    companion object
    {
        fun deserialize(obj: Any): ActionBarFeedback?
        {
            if (obj !is String)
                return null

            return ActionBarFeedback(obj.miniParse())
        }
    }
}