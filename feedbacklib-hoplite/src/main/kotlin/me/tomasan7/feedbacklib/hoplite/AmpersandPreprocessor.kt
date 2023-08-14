package me.tomasan7.feedbacklib.hoplite

import com.sksamuel.hoplite.*
import com.sksamuel.hoplite.decoder.toValidated
import com.sksamuel.hoplite.preprocessor.Preprocessor

object AmpersandPreprocessor : Preprocessor
{
    override fun process(node: Node, context: DecoderContext) =
        Result.success(replaceAmpersands(node)).toValidated { ConfigFailure.PreprocessorFailure("Failed to replace ampersands.", it) }

    private fun replaceAmpersands(node: Node): Node
    {
        return when (node)
        {
            is StringNode -> return node.copy(value = node.value.replace('&', '§'))
            is MapNode -> return node.copy(map = node.map.mapValues { replaceAmpersands(it.value) })
            is ArrayNode  -> return node.copy(elements = node.elements.map { replaceAmpersands(it) })
            else -> node
        }
    }
}
