package me.tomasan7.feedbacklib.feedbackelement

import me.tomasan7.feedbacklib.Feedback
import me.tomasan7.feedbacklib.buildTextComponent
import me.tomasan7.feedbacklib.miniParse
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent

open class ChatFeedback(val message: Component) : Feedback
{
    override fun apply(audience: Audience) = audience.sendMessage(message)

    override fun serialize() = Feedback.legacySerializer.serialize(message)

    companion object
    {
        fun deserialize(obj: Any): ChatFeedback?
        {
            if (obj !is String)
                return null

            return ChatFeedback(obj.miniParse())
        }
    }
}

fun buildChatFeedback(
    text: String? = null,
    block: TextComponent.Builder.() -> Unit = {}
) = ChatFeedback(buildTextComponent(text, block))
