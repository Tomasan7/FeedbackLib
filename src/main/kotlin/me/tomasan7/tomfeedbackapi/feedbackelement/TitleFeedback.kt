package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Placeholders
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.title
import net.kyori.adventure.title.TitlePart
import java.time.Duration

class TitleFeedback(
    val title: String? = null,
    val subtitle: String? = null,
    val times: Times = Times.DEFAULT,
    private val miniMessage: MiniMessage = MiniMessage.miniMessage())
{
    init
    {
        if (title == null && subtitle == null)
            throw IllegalArgumentException("Title and subtitle cannot be null at the same time. At least one must be non null.")
    }

    /**
     * Creates a [Title] instance from this [TitleFeedback].
     * @param placeholders Placeholders to replace.
     */
    fun build(placeholders: Placeholders? = null): Title
    {
        val titleComp = if (title != null)
            if (placeholders != null) miniMessage.deserialize(placeholders.apply(title))
            else miniMessage.deserialize(title)
        else
            Component.empty()

        val subtitleComp = if (subtitle != null)
            if (placeholders != null) miniMessage.deserialize(placeholders.apply(subtitle))
            else miniMessage.deserialize(subtitle)
        else
            Component.empty()

        return title(titleComp, subtitleComp, times.titleTimes)
    }

    /**
     * Creates [Component] representing title.
     * @param placeholders Placeholders to replace.
     */
    fun buildTitle(placeholders: Placeholders? = null): Component?
    {
        if (title == null)
            return null

        return if (placeholders != null)
            miniMessage.deserialize(placeholders.apply(title))
        else
            miniMessage.deserialize(title)
    }

    /**
     * Creates [Component] representing subtitle.
     * @param placeholders Placeholders to replace.
     */
    fun buildSubtitle(placeholders: Placeholders? = null): Component?
    {
        if (subtitle == null)
            return null

        return if (placeholders != null)
            miniMessage.deserialize(placeholders.apply(subtitle))
        else
            miniMessage.deserialize(subtitle)
    }

    /**
     * Sends this [TitleFeedback] to the [player].
     */
    fun send(player: Audience, placeholders: Placeholders? = null)
    {
        /* Send whole Title object if both title and subtitle are present. */
        if (title != null && subtitle != null)
            player.showTitle(build(placeholders))

        /* Now, we will only send one part (either title or subtitle) so we need to send times separately beforehand. */
        player.sendTitlePart(TitlePart.TIMES, times.titleTimes)

        /* Send only title. */
        if (title != null)
            player.sendTitlePart(TitlePart.TITLE, buildTitle(placeholders) as Component)
        /* Send only subtitle. */
        else if (subtitle != null)
            player.sendTitlePart(TitlePart.SUBTITLE, buildSubtitle(placeholders) as Component)
    }

    companion object
    {
        fun deserialize(obj: Any, miniMessage: MiniMessage? = null): TitleFeedback?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return TitleFeedback(obj)
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

                return if (miniMessage != null)
                    TitleFeedback(title,
                                  subtitle,
                                  Times(fadeIn,
                                        stay,
                                        fadeOut),
                                  miniMessage)
                else
                    TitleFeedback(title,
                                  subtitle,
                                  Times(fadeIn,
                                        stay,
                                        fadeOut))
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