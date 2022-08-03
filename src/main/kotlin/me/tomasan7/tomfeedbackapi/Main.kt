package me.tomasan7.tomfeedbackapi

import me.tomasan7.tomfeedbackapi.componentbuilding.buildComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

fun main()
{
    buildComponent {
        text("You're a ")
        color(TextColor.color(0x443344))
        keybind("key.jump") {
            color(NamedTextColor.LIGHT_PURPLE)
            decoration(TextDecoration.BOLD, true)
        }
        text(" to jump!")
    }
}