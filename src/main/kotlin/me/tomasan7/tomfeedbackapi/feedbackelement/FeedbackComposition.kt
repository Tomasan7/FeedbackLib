package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Feedback
import me.tomasan7.tomfeedbackapi.emptyMutableLinkedList
import net.kyori.adventure.audience.Audience

class FeedbackComposition(
    vararg val feedbacks: Feedback
) : Feedback
{
    constructor(feedbacks: Collection<Feedback>): this(*feedbacks.toTypedArray())

    override fun apply(audience: Audience) = feedbacks.forEach { it.apply(audience) }

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

                val messageObj = map["message"]
                val titleObj = map["title"]
                val actionBarObj = map["action-bar"]
                val soundObj = map["sound"]

                val feedbacks = emptyMutableLinkedList<Feedback>()

                messageObj?.let { nnMessageObj -> ChatFeedback.deserialize(nnMessageObj)?.let { feedbacks.add(it) } }
                titleObj?.let { nnTitleObj -> TitleFeedback.deserialize(nnTitleObj)?.let { feedbacks.add(it) } }
                actionBarObj?.let { nnActionBarObj -> ActionBarFeedback.deserialize(nnActionBarObj)?.let { feedbacks.add(it) } }
                soundObj?.let { nnSoundObj -> SoundFeedback.deserialize(nnSoundObj)?.let { feedbacks.add(it) } }

                return FeedbackComposition(feedbacks)
            }
        }
    }
}

fun buildFeedbackComposition(block: FeedbackComposition.Builder.() -> Unit) = FeedbackComposition.builder().apply(block).build()