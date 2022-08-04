package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Feedback
import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.PlaceholderFeedback
import me.tomasan7.tomfeedbackapi.emptyMutableLinkedList
import net.kyori.adventure.audience.Audience

class PlaceholderFeedbackComposition(
    vararg val feedbacks: Feedback
) : PlaceholderFeedback
{
    override fun apply(audience: Audience, placeholders: Placeholders?) = feedbacks.forEach { feedback ->
        if (feedback is PlaceholderFeedback)
            feedback.apply(audience, placeholders)
        else
            feedback.apply(audience)
    }


    companion object
    {
        fun deserialize(obj: Any): FeedbackComposition?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return ChatFeedback.deserialize(obj)?.let { FeedbackComposition(it) }
            else
            {
                val map = obj as Map<String, *>

                val messageObj = map["message"]
                val titleObj = map["title"]
                val actionBarObj = map["action-bar"]
                val soundObj = map["sound"]

                val feedbacks = emptyMutableLinkedList<Feedback>()

                messageObj?.let { nnMessageObj -> ChatFeedback.deserialize(nnMessageObj)?.let { feedbacks.add(it) } }
                titleObj?.let { nnTitleObj -> TitleFeedback.deserialize(nnTitleObj)?.let { feedbacks.add(it) } }
                actionBarObj?.let { nnActionBarObj ->
                    ActionBarFeedback.deserialize(nnActionBarObj)?.let { feedbacks.add(it) }
                }
                soundObj?.let { nnSoundObj -> SoundFeedback.deserialize(nnSoundObj)?.let { feedbacks.add(it) } }

                return FeedbackComposition(*feedbacks.toTypedArray())
            }
        }
    }
}