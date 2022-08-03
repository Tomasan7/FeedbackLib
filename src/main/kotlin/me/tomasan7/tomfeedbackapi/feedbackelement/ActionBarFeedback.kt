package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience

class ActionBarFeedback(
    message: String
) : MessageFeedback(message)
{
    /**
     * Sends this [MessageFeedback] to the [player].
     */
    override fun send(player: Audience, placeholders: Placeholders?) = player.sendActionBar(message.miniParse(placeholders))

    companion object
    {
        fun deserialize(obj: Any): ActionBarFeedback?
        {
            if (obj !is String)
                return null

            return ActionBarFeedback(obj)
        }
    }
}