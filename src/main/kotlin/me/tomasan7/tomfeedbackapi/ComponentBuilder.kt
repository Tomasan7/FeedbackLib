package me.tomasan7.tomfeedbackapi

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentBuilder
import net.kyori.adventure.text.KeybindComponent
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.TranslatableComponent

fun buildTextComponent(
    text: String? = null,
    block: TextComponent.Builder.() -> Unit = {}
): TextComponent
{
    val builder = Component.text()
    text?.let { builder.content(text) }
    builder.block()
    return builder.build()
}

fun ComponentBuilder<*, *>.textComponent(
    text: String? = null,
    block: TextComponent.Builder.() -> Unit = {}
)
{
    append(buildTextComponent(text, block))
}

fun buildKeybindComponent(
    keybind: String? = null,
    block: KeybindComponent.Builder.() -> Unit
): KeybindComponent
{
    val builder = Component.keybind()
    keybind?.let { builder.keybind(keybind) }
    builder.block()
    return builder.build()
}

fun ComponentBuilder<*, *>.keybindComponent(
    keybind: String? = null,
    block: KeybindComponent.Builder.() -> Unit = {}
)
{
    append(buildKeybindComponent(keybind, block))
}

fun buildTranslatableComponent(
    key: String? = null,
    block: TranslatableComponent.Builder.() -> Unit
): TranslatableComponent
{
    val builder = Component.translatable()
    key?.let { builder.key(key) }
    builder.block()
    return builder.build()
}

fun ComponentBuilder<*, *>.translatableComponent(
    key: String? = null,
    block: TranslatableComponent.Builder.() -> Unit = {}
)
{
    append(buildTranslatableComponent(key, block))
}