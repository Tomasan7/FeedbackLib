package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Feedback
import me.tomasan7.tomfeedbackapi.emptyMutableHashMap
import me.tomasan7.tomfeedbackapi.emptyMutableLinkedList
import net.kyori.adventure.audience.Audience
import java.lang.IllegalStateException

class FeedbackComposition(
    vararg val feedbacks: Feedback
) : Feedback
{
    constructor(feedbacks: Collection<Feedback>) : this(*feedbacks.toTypedArray())

    override fun apply(audience: Audience) = feedbacks.forEach { it.apply(audience) }

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
                is ChatFeedback, is ChatPlaceholderFeedback -> Keys.MESSAGE
                is ActionBarFeedback, is ActionBarPlaceholderFeedback -> Keys.ACTION_BAR
                is TitleFeedback, is TitlePlaceholderFeedback -> Keys.TITLE
                is SoundFeedback -> Keys.SOUND
                else -> throw IllegalStateException()
            }

            map[key] = feedback.serialize()
        }

        return map
    }

    class Builder
    {
        private val feedbacks = emptyMutableLinkedList<Feedback>()

        fun add(feedback: Feedback) = feedbacks.add(feedback)

        fun build() = FeedbackComposition(*feedbacks.toTypedArray())
    }

    companion object
    {
        fun builder() = Builder()

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

                val messageObj = map[Keys.MESSAGE]
                val titleObj = map[Keys.TITLE]
                val actionBarObj = map[Keys.ACTION_BAR]
                val soundObj = map[Keys.SOUND]

                val feedbacks = emptyMutableLinkedList<Feedback>()

                messageObj?.let { nnMessageObj -> ChatFeedback.deserialize(nnMessageObj)?.let { feedbacks.add(it) } }
                titleObj?.let { nnTitleObj -> TitleFeedback.deserialize(nnTitleObj)?.let { feedbacks.add(it) } }
                actionBarObj?.let { nnActionBarObj ->
                    ActionBarFeedback.deserialize(nnActionBarObj)?.let { feedbacks.add(it) }
                }
                soundObj?.let { nnSoundObj -> SoundFeedback.deserialize(nnSoundObj)?.let { feedbacks.add(it) } }

                return FeedbackComposition(feedbacks)
            }
        }

        object Keys
        {
            const val MESSAGE = "message"
            const val TITLE = "title"
            const val ACTION_BAR = "action-bar"
            const val SOUND = "sound"
        }
    }
}

fun buildFeedbackComposition(block: FeedbackComposition.Builder.() -> Unit) =
    FeedbackComposition.builder().apply(block).build()