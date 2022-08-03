package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.TextFeedback
import net.kyori.adventure.audience.Audience

class FeedbackComposition(
    val message: ChatFeedback? = null,
    val title: TitleFeedback? = null,
    val actionBar: ActionBarFeedbackChat? = null,
    val sound: SoundFeedback? = null
) : TextFeedback
{
    override fun apply(audience: Audience, placeholders: Placeholders?)
    {
        message?.apply(audience, placeholders)
        title?.apply(audience, placeholders)
        actionBar?.apply(audience, placeholders)
        sound?.apply(audience)
    }

    companion object
    {
        fun deserialize(obj: Any): FeedbackComposition?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return FeedbackComposition(ChatFeedback.deserialize(obj))
            else
            {
                val map = obj as Map<String, Any>

                val messageObj = map["message"]
                val titleObj = map["title"]
                val actionBarObj = map["action-bar"]
                val soundObj = map["sound"]

                return FeedbackComposition(
                    if (messageObj != null) ChatFeedback.deserialize(messageObj) else null,
                    if (titleObj != null) TitleFeedback.deserialize(titleObj) else null,
                    if (actionBarObj != null) ActionBarFeedbackChat.deserialize(actionBarObj) else null,
                    if (soundObj != null) SoundFeedback.deserialize(soundObj) else null
                )
            }
        }
    }
}