package me.tomasan7.feedbacklib

import net.kyori.adventure.audience.Audience

interface PlaceholderFeedback : Feedback
{
    fun apply(audience: Audience, placeholders: Placeholders? = null)

    override fun apply(audience: Audience) = apply(audience, null)
}

fun Audience.apply(feedback: PlaceholderFeedback, placeholders: Placeholders?) = feedback.apply(this, placeholders)
