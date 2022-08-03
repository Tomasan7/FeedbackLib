package me.tomasan7.tomfeedbackapi

import net.kyori.adventure.audience.Audience

interface TextFeedback : Feedback
{
    fun apply(audience: Audience, placeholders: Placeholders? = null)

    override fun apply(audience: Audience) = apply(audience, null)
}