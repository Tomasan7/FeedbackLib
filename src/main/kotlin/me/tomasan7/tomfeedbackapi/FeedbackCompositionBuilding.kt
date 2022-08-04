package me.tomasan7.tomfeedbackapi

import me.tomasan7.tomfeedbackapi.feedbackelement.*
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.TextComponent

fun FeedbackComposition.Builder.chatFeedback(
    text: String? = null,
    block: TextComponent.Builder.() -> Unit = {}
) = add(ChatFeedback(buildTextComponent(text, block)))

fun FeedbackComposition.Builder.actionBarFeedback(
    text: String? = null,
    block: TextComponent.Builder.() -> Unit = {}
) = add(ActionBarFeedback(buildTextComponent(text, block)))

fun FeedbackComposition.Builder.soundFeedback(
    key: Key,
    block: SoundFeedback.Builder.() -> Unit = {}
) = add(buildSoundFeedback(key, block))

fun FeedbackComposition.Builder.soundFeedback(
    key: String,
    block: SoundFeedback.Builder.() -> Unit = {}
) = soundFeedback(Key.key(key), block)

fun FeedbackComposition.Builder.titleFeedback(
    block: TitleFeedback.Builder.() -> Unit = {}
) = add(buildTitleFeedback(block))