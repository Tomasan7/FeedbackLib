package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Placeholders
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.MiniMessage

class ActionBarFeedback(
    message: String,
    miniMessage: MiniMessage = MiniMessage.miniMessage()
) : MessageFeedback(message, miniMessage)
{
    /**
     * Sends this [MessageFeedback] to the [player].
     */
    override fun send(player: Audience, placeholders: Placeholders?) = player.sendActionBar(build(placeholders))

    companion object
    {
        fun deserialize(obj: Any, miniMessage: MiniMessage? = null): ActionBarFeedback?
        {
            if (obj !is String)
                return null

            return if (miniMessage != null)
                ActionBarFeedback(obj, miniMessage)
            else
                ActionBarFeedback(obj)
        }
    }
}