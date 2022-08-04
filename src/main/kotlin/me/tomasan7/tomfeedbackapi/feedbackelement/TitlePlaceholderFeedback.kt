package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Placeholders
import me.tomasan7.tomfeedbackapi.PlaceholderFeedback
import me.tomasan7.tomfeedbackapi.miniParse
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import java.time.Duration

class TitlePlaceholderFeedback(
    val title: String? = null,
    val subtitle: String? = null,
    val times: Times = Times.DEFAULT
) : PlaceholderFeedback
{
    init
    {
        require(title != null || subtitle != null) { "Title and subtitle cannot be null at the same time. At least one must be non null." }
    }

    override fun apply(audience: Audience, placeholders: Placeholders?)
    {
        /* Send whole Title object if both title and subtitle are present. */
        if (title != null && subtitle != null)
        {
            audience.showTitle(buildTitle(title, subtitle, placeholders))
            return
        }

        /* Now, we will only send one part (either title or subtitle) so we need to send times separately beforehand. */
        audience.sendTitlePart(TitlePart.TIMES, times.titleTimes)

        /* Send only title. */
        if (title != null)
            audience.sendTitlePart(TitlePart.TITLE, buildTitleComponent(title, placeholders))
        /* Send only subtitle. */
        else if (subtitle != null)
            audience.sendTitlePart(TitlePart.SUBTITLE, buildSubtitleComponent(subtitle, placeholders))
    }

    /**
     * Creates a [Title] instance from this [TitlePlaceholderFeedback].
     * @param placeholders Placeholders to replace.
     */
    private fun buildTitle(title: String, subtitle: String, placeholders: Placeholders? = null): Title
    {
        val titleComp = buildTitleComponent(title, placeholders)
        val subtitleComp = buildSubtitleComponent(subtitle, placeholders)

        return Title.title(titleComp, subtitleComp, times.titleTimes)
    }

    /**
     * Creates [Component] representing title.
     * @param placeholders Placeholders to replace.
     */
    private fun buildTitleComponent(title: String, placeholders: Placeholders? = null) = title.miniParse(placeholders)

    /**
     * Creates [Component] representing subtitle.
     * @param placeholders Placeholders to replace.
     */
    private fun buildSubtitleComponent(subtitle: String, placeholders: Placeholders? = null) = subtitle.miniParse(placeholders)

    companion object
    {
        fun deserialize(obj: Any): TitlePlaceholderFeedback?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return TitlePlaceholderFeedback(obj)
            else
            {
                val map = obj as Map<*, *>

                val title = map["title"] as? String
                val subtitle = map["sub-title"] as? String

                if (title == null && subtitle == null)
                    return null

                val fadeIn = map["fade-in"] as? Int ?: Times.DEFAULT.fadeIn
                val stay = map["stay"] as? Int ?: Times.DEFAULT.stay
                val fadeOut = map["fade-out"] as? Int ?: Times.DEFAULT.fadeOut

                return TitlePlaceholderFeedback(title,
                                                subtitle,
                                                Times(fadeIn, stay, fadeOut))
            }
        }

        data class Times(val fadeIn: Int, val stay: Int, val fadeOut: Int)
        {
            val titleTimes: Title.Times
                get() = Title.Times.times(
                    Duration.ofMillis((fadeIn * 50).toLong()),
                    Duration.ofMillis((stay * 50).toLong()),
                    Duration.ofMillis((fadeOut * 50).toLong())
                )

            companion object
            {
                val DEFAULT = Times(20, 20, 20)
            }
        }
    }
}