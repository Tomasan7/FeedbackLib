package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.*
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.TitlePart
import java.time.Duration

class TitleFeedback(
    val title: Component? = null,
    val subtitle: Component? = null,
    val times: Times = Times.DEFAULT
) : Feedback
{
    init
    {
        require(title != null || subtitle != null) { "Title and subtitle cannot be null at the same time. At least one must be non null." }
    }

    override fun apply(audience: Audience)
    {
        /* Send whole Title object if both title and subtitle are present. */
        if (title != null && subtitle != null)
        {
            audience.showTitle(buildTitle())
            return
        }

        /* Now, we will only send one part (either title or subtitle) so we need to send times separately beforehand. */
        audience.sendTitlePart(TitlePart.TIMES, times.titleTimes)

        /* Send only title. */
        if (title != null)
            audience.sendTitlePart(TitlePart.TITLE, title)
        /* Send only subtitle. */
        else if (subtitle != null)
            audience.sendTitlePart(TitlePart.SUBTITLE, subtitle)
    }

    override fun serialize(): Map<String, Any>
    {
        val map = emptyMutableHashMap<String, Any>()

        if (title != null)
            map[Keys.TITLE] = Feedback.legacySerializer.serialize(title)
        if (subtitle != null)
            map[Keys.SUB_TITLE] = Feedback.legacySerializer.serialize(subtitle)
        if (times.fadeIn != Times.DEFAULT.fadeIn)
            map[Keys.FADE_IN] = times.fadeIn
        if (times.stay != Times.DEFAULT.stay)
            map[Keys.STAY] = times.stay
        if (times.fadeOut != Times.DEFAULT.fadeOut)
            map[Keys.FADE_OUT] = times.fadeOut

        return map
    }

    /**
     * Creates a [Title] instance from this [TitleFeedback].
     * @param placeholders Placeholders to replace.
     */
    private fun buildTitle(): Title
    {
        /* Neither should be null, since it is called from apply, when neither is null. */
        val titleComp = title ?: Component.empty()
        val subtitleComp = subtitle ?: Component.empty()

        return Title.title(titleComp, subtitleComp, times.titleTimes)
    }

    class Builder
    {
        private var title: Component? = null
        private var subtitle: Component? = null
        private var times = Times.DEFAULT

        fun title(title: Component) { this.title = title }

        fun title(
            text: String? = null,
            block: TextComponent.Builder.() -> Unit = {}
        ) { title = buildTextComponent(text, block) }

        fun subtitle(subtitle: Component) { this.subtitle = subtitle }

        fun subtitle(
            text: String? = null,
            block: TextComponent.Builder.() -> Unit = {}
        ) { subtitle = buildTextComponent(text, block) }

        fun times(times: Times) { this.times = times }

        fun fadeIn(fadeIn: Int) { times = times.copy(fadeIn = fadeIn) }

        fun stay(stay: Int) { times = times.copy(stay = stay) }

        fun fadeOut(fadeOut: Int) { times = times.copy(fadeOut = fadeOut) }

        fun build() = TitleFeedback(title, subtitle, times)
    }

    companion object
    {
        fun builder() = Builder()

        fun deserialize(obj: Any): TitleFeedback?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return TitleFeedback(obj.miniParse())
            else
            {
                val map = obj as Map<*, *>

                val title = map[Keys.TITLE] as? String
                val subtitle = map[Keys.SUB_TITLE] as? String

                if (title == null && subtitle == null)
                    return null

                val fadeIn = (map[Keys.FADE_IN] as? Number)?.toInt() ?: Times.DEFAULT.fadeIn
                val stay = (map[Keys.STAY] as? Number)?.toInt() ?: Times.DEFAULT.stay
                val fadeOut = (map[Keys.FADE_OUT]as? Number)?.toInt() ?: Times.DEFAULT.fadeOut

                return TitleFeedback(title?.miniParse(),
                                     subtitle?.miniParse(),
                                     Times(fadeIn, stay, fadeOut))
            }
        }

        object Keys
        {
            const val TITLE = "title"
            const val SUB_TITLE = "sub-title"
            const val FADE_IN = "fade-in"
            const val STAY = "stay"
            const val FADE_OUT = "fade-out"
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

fun buildTitleFeedback(block: TitleFeedback.Builder.() -> Unit = {}) = TitleFeedback.builder().apply(block).build()
