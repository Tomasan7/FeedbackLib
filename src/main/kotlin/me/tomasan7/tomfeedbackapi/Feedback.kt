package me.tomasan7.tomfeedbackapi

import me.tomasan7.tomfeedbackapi.feedbackelement.ActionBarFeedback
import me.tomasan7.tomfeedbackapi.feedbackelement.MessageFeedback
import me.tomasan7.tomfeedbackapi.feedbackelement.SoundFeedback
import me.tomasan7.tomfeedbackapi.feedbackelement.TitleFeedback
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.MiniMessage

class Feedback(
    val message: MessageFeedback? = null,
    val title: TitleFeedback? = null,
    val actionBar: ActionBarFeedback? = null,
    val sound: SoundFeedback? = null
)
{
    fun send(player: Audience, placeholders: Placeholders? = null)
    {
        message?.send(player, placeholders)
        title?.send(player, placeholders)
        actionBar?.send(player, placeholders)
        sound?.send(player)
    }

    companion object
    {
        /** The [MiniMessage] instance used by feedback elements to deserialize mini-message strings. */
        var miniMessage = MiniMessage.miniMessage()

        fun deserialize(obj: Any): Feedback?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return Feedback(MessageFeedback.deserialize(obj))
            else
            {
                val map = obj as Map<String, Any>

                val messageObj = map["message"]
                val titleObj = map["title"]
                val actionBarObj = map["action-bar"]
                val soundObj = map["sound"]

                return Feedback(
                    if (messageObj != null) MessageFeedback.deserialize(messageObj) else null,
                    if (titleObj != null) TitleFeedback.deserialize(titleObj) else null,
                    if (actionBarObj != null) ActionBarFeedback.deserialize(actionBarObj) else null,
                    if (soundObj != null) SoundFeedback.deserialize(soundObj) else null
                )
            }
        }
    }
}

fun String.miniParse(placeholders: Placeholders?) =
    if (placeholders != null)
        Feedback.miniMessage.deserialize(placeholders.apply(this))
    else
        Feedback.miniMessage.deserialize(this)