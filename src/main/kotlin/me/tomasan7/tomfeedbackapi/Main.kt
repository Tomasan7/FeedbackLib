package me.tomasan7.tomfeedbackapi

import me.tomasan7.tomfeedbackapi.feedbackelement.buildFeedbackComposition

fun main()
{
    buildFeedbackComposition {
        chatFeedback("Gay") {

        }
        soundFeedback("rabbit.noise") {

        }
        titleFeedback {
            title("GAY")
            stay(50)
        }
    }
}