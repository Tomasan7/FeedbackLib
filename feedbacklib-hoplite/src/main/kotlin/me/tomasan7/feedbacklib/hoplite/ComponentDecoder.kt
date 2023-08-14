package me.tomasan7.feedbacklib.hoplite

import com.sksamuel.hoplite.*
import com.sksamuel.hoplite.decoder.Decoder
import com.sksamuel.hoplite.fp.invalid
import com.sksamuel.hoplite.fp.valid
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import kotlin.reflect.KType

object ComponentDecoder : Decoder<Component>
{
    override fun decode(node: Node, type: KType, context: DecoderContext): ConfigResult<Component>
    {
        if (node !is StringNode)
            return ConfigFailure.DecodeError(node, type).invalid()

        return MiniMessage.miniMessage().deserialize(node.value).valid()
    }

    override fun supports(type: KType) = type.classifier == Component::class
}
