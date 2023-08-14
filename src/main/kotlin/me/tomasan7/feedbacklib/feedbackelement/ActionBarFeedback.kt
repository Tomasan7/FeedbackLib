package me.tomasan7.feedbacklib.feedbackelement

import me.tomasan7.feedbacklib.Feedback
import me.tomasan7.feedbacklib.buildTextComponent
import me.tomasan7.feedbacklib.miniParse
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent

class ActionBarFeedback(val message: Component) : Feedback
{
    override fun apply(audience: Audience) = audience.sendActionBar(message)

    override fun serialize() = Feedback.legacySerializer.serialize(message)

    companion object
    {
        fun deserialize(obj: Any): ActionBarFeedback?
        {
            if (obj !is String)
                return null

            return ActionBarFeedback(obj.miniParse())
        }
    }
}

fun buildActionBarFeedback(
    text: String? = null,
    block: TextComponent.Builder.() -> Unit = {}
) = ActionBarFeedback(buildTextComponent(text, block))
