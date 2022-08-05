package me.tomasan7.tomfeedbackapi.feedbackelement

import me.tomasan7.tomfeedbackapi.Feedback
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

class SoundFeedback(
    val key: Key,
    val source: Sound.Source = DEFAULT_SOURCE,
    val volume: Float = DEFAULT_VOLUME,
    val pitch: Float = DEFAULT_PITCH
) : Feedback
{
    override fun apply(audience: Audience) = audience.playSound(Sound.sound(key, source, volume, pitch), Sound.Emitter.self())

    override fun serialize() = mapOf(
        Keys.KEY to key.toString(),
        Keys.SOURCE to source.toString(),
        Keys.VOLUME to volume,
        Keys.PITCH to pitch
    )

    class Builder(private val key: Key)
    {
        constructor(key: String) : this(Key.key(key))

        private var source = DEFAULT_SOURCE
        private var volume = DEFAULT_VOLUME
        private var pitch = DEFAULT_PITCH

        fun source(source: Sound.Source) { this.source = source }

        fun volume(volume: Float) { this.volume = volume }

        fun pitch(pitch: Float) { this.pitch = pitch }

        fun build() = SoundFeedback(key, source, volume, pitch)
    }

    companion object
    {
        const val DEFAULT_VOLUME = 1f
        const val DEFAULT_PITCH = 1f
        val DEFAULT_SOURCE = Sound.Source.NEUTRAL

        fun builder(key: Key) = Builder(key)

        fun builder(key: String) = Builder(key)

        fun deserialize(obj: Any): SoundFeedback?
        {
            if (obj !is String
                && obj !is Map<*, *>)
                return null

            if (obj is String)
                return SoundFeedback(Key.key(obj))
            else
            {
                val map = obj as Map<String, Any>

                val key = map[Keys.KEY] as? String ?: return null

                val source = map[Keys.SOURCE] as? String ?: DEFAULT_SOURCE.toString()
                val volume = (map[Keys.VOLUME] as? Number)?.toFloat() ?: DEFAULT_VOLUME
                val pitch = (map[Keys.PITCH] as? Number)?.toFloat() ?: DEFAULT_PITCH

                return SoundFeedback(Key.key(key), Sound.Source.valueOf(source), volume, pitch)
            }
        }

        object Keys
        {
            const val KEY = "key"
            const val SOURCE = "source"
            const val VOLUME = "volume"
            const val PITCH = "pitch"
        }
    }
}

fun buildSoundFeedback(
    key: Key,
    block: SoundFeedback.Builder.() -> Unit = {}
) = SoundFeedback.builder(key).apply(block).build()

fun buildSoundFeedback(
    key: String,
    block: SoundFeedback.Builder.() -> Unit = {}
) = buildSoundFeedback(Key.key(key), block)