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
        fun deserialize(obj: Any, miniMessage: MiniMessage? = null): Feedback?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return if (miniMessage != null) Feedback(MessageFeedback.deserialize(obj))
                else Feedback(MessageFeedback.deserialize(obj))
            else
            {
                val map = obj as Map<String, Any>

                val messageObj = map["message"]
                val titleObj = map["title"]
                val actionBarObj = map["action-bar"]
                val soundObj = map["sound"]

                return Feedback(
                    if (messageObj != null) MessageFeedback.deserialize(messageObj, miniMessage) else null,
                    if (titleObj != null) TitleFeedback.deserialize(titleObj, miniMessage) else null,
                    if (actionBarObj != null) ActionBarFeedback.deserialize(actionBarObj, miniMessage) else null,
                    if (soundObj != null) SoundFeedback.deserialize(soundObj) else null
                )
            }
        }
    }
}