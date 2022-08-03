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

    companion object
    {
        const val DEFAULT_VOLUME = 1f
        const val DEFAULT_PITCH = 1f
        val DEFAULT_SOURCE = Sound.Source.NEUTRAL

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

                val key = map["key"] as? String ?: return null

                val source = map["source"] as? String ?: DEFAULT_SOURCE.toString()
                val volume = map["volume"] as? Float ?: DEFAULT_VOLUME
                val pitch = map["pitch"] as? Float ?: DEFAULT_PITCH

                return SoundFeedback(Key.key(key), Sound.Source.valueOf(source), volume, pitch)
            }
        }
    }
}