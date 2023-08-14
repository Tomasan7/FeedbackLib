package me.tomasan7.feedbacklib.hoplite

import com.sksamuel.hoplite.*
import com.sksamuel.hoplite.decoder.Decoder
import com.sksamuel.hoplite.decoder.toValidated
import com.sksamuel.hoplite.fp.invalid
import me.tomasan7.feedbacklib.Feedback
import me.tomasan7.feedbacklib.PlaceholderFeedback
import me.tomasan7.feedbacklib.feedbackelement.FeedbackComposition
import me.tomasan7.feedbacklib.feedbackelement.PlaceholderFeedbackComposition
import kotlin.reflect.KType

object FeedbackDecoder : Decoder<Feedback>
{
    override fun decode(node: Node, type: KType, context: DecoderContext): ConfigResult<FeedbackComposition> =
        when (node)
        {
            // To be honest, I don't understand any of this Arrow stuff, but it works like this.
            is StringNode -> runCatching { FeedbackComposition.deserialize(node.value)!! }
                    .toValidated { ConfigFailure.DecodeError(node, type) }
            is MapNode    -> runCatching { FeedbackComposition.deserialize(node.toObjMap())!! }
                    .toValidated { ConfigFailure.DecodeError(node, type) }
            else -> ConfigFailure.DecodeError(node, type).invalid()
        }

    override fun supports(type: KType) = type.classifier == Feedback::class
}

object PlaceholderFeedbackDecoder : Decoder<PlaceholderFeedback>
{
    override fun decode(node: Node, type: KType, context: DecoderContext): ConfigResult<PlaceholderFeedbackComposition> =
        when (node)
        {
            // To be honest, I don't understand any of this Arrow stuff, but it works like this.
            is StringNode -> runCatching { PlaceholderFeedbackComposition.deserialize(node.value)!! }
                    .toValidated { ConfigFailure.DecodeError(node, type) }
            is MapNode    -> runCatching { PlaceholderFeedbackComposition.deserialize(node.toObjMap())!! }
                    .toValidated { ConfigFailure.DecodeError(node, type) }
            else -> ConfigFailure.DecodeError(node, type).invalid()
        }

    override fun supports(type: KType) = type.classifier == PlaceholderFeedback::class
}

private fun MapNode.toObjMap(): Map<String, Any?>
{
    val result = mutableMapOf<String, Any?>()

    for ((key, node) in map.entries)
        result[key] = when (node)
        {
            is PrimitiveNode -> node.value
            is MapNode       -> node.toObjMap()
            else             -> null
        }

    return result
}
