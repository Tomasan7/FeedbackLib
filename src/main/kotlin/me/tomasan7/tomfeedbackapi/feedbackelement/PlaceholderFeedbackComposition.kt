package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.*
import net.kyori.adventure.audience.Audience
import java.lang.IllegalStateException

class PlaceholderFeedbackComposition(
    vararg val feedbacks: Feedback
) : PlaceholderFeedback
{
    constructor(feedbacks: Collection<Feedback>): this(*feedbacks.toTypedArray())

    override fun apply(audience: Audience, placeholders: Placeholders?) = feedbacks.forEach { feedback ->
        if (feedback is PlaceholderFeedback)
            feedback.apply(audience, placeholders)
        else
            feedback.apply(audience)
    }

    /**
     * Only serializes one [Feedback] of each type.
     */
    override fun serialize(): Any
    {
        val map = emptyMutableHashMap<String, Any>()

        for (feedback in feedbacks)
        {
            val key = when (feedback)
            {
                is ChatFeedback, is ChatPlaceholderFeedback -> FeedbackComposition.Companion.Keys.MESSAGE
                is ActionBarFeedback, is ActionBarPlaceholderFeedback -> FeedbackComposition.Companion.Keys.ACTION_BAR
                is TitleFeedback, is TitlePlaceholderFeedback -> FeedbackComposition.Companion.Keys.TITLE
                is SoundFeedback -> FeedbackComposition.Companion.Keys.SOUND
                else -> throw IllegalStateException()
            }

            map[key] = feedback.serialize()
        }

        return map
    }

    companion object
    {
        fun deserialize(obj: Any): PlaceholderFeedbackComposition?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return ChatPlaceholderFeedback.deserialize(obj)?.let { PlaceholderFeedbackComposition(it) }
            else
            {
                val map = obj as Map<String, *>

                val messageObj = map[FeedbackComposition.Companion.Keys.MESSAGE]
                val titleObj = map[FeedbackComposition.Companion.Keys.TITLE]
                val actionBarObj = map[FeedbackComposition.Companion.Keys.ACTION_BAR]
                val soundObj = map[FeedbackComposition.Companion.Keys.SOUND]

                val feedbacks = emptyMutableLinkedList<Feedback>()

                messageObj?.let { nnMessageObj -> ChatPlaceholderFeedback.deserialize(nnMessageObj)?.let { feedbacks.add(it) } }
                titleObj?.let { nnTitleObj -> TitlePlaceholderFeedback.deserialize(nnTitleObj)?.let { feedbacks.add(it) } }
                actionBarObj?.let { nnActionBarObj -> ActionBarPlaceholderFeedback.deserialize(nnActionBarObj)?.let { feedbacks.add(it) } }
                soundObj?.let { nnSoundObj -> SoundFeedback.deserialize(nnSoundObj)?.let { feedbacks.add(it) } }

                return PlaceholderFeedbackComposition(feedbacks)
            }
        }
    }
}
