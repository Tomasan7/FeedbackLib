package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.*
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

    override fun serialize(): Map<String, Any>
    {
        val map = emptyMutableHashMap<String, Any>()

        if (title != null)
            map[TitleFeedback.Companion.Keys.TITLE] = title
        if (subtitle != null)
            map[TitleFeedback.Companion.Keys.SUB_TITLE] = subtitle
        if (times.fadeIn != TitleFeedback.Companion.Times.DEFAULT.fadeIn)
            map[TitleFeedback.Companion.Keys.FADE_IN] = times.fadeIn
        if (times.stay != TitleFeedback.Companion.Times.DEFAULT.stay)
            map[TitleFeedback.Companion.Keys.STAY] = times.stay
        if (times.fadeOut != TitleFeedback.Companion.Times.DEFAULT.fadeOut)
            map[TitleFeedback.Companion.Keys.FADE_OUT] = times.fadeOut

        return map
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

                val title = map[TitleFeedback.Companion.Keys.TITLE] as? String
                val subtitle = map[TitleFeedback.Companion.Keys.SUB_TITLE] as? String

                if (title == null && subtitle == null)
                    return null

                val fadeIn = (map[TitleFeedback.Companion.Keys.FADE_IN]as? Number)?.toInt() ?: Times.DEFAULT.fadeIn
                val stay = (map[TitleFeedback.Companion.Keys.STAY]as? Number)?.toInt() ?: Times.DEFAULT.stay
                val fadeOut = (map[TitleFeedback.Companion.Keys.FADE_OUT]as? Number)?.toInt() ?: Times.DEFAULT.fadeOut

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
