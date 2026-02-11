package com.markoapps.aisummerizer.data.remote.model

import com.markoapps.aisummerizer.domain.model.NoteSummary
import kotlinx.serialization.Serializable

@Serializable
data class NoteSummaryJson(
    val tldr: String,
    val summary: String,
    val actionItems: List<String>,
    val deadlines: List<String>,
    val tags: List<String>
)

fun NoteSummaryJson.toNoteSummary(): NoteSummary {
    return NoteSummary(
        id = "",
        tldr = tldr,
        summary = summary,
        actionItems = actionItems,
        deadlines = deadlines,
        tags = tags
    )
}