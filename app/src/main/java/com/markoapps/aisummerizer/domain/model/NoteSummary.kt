package com.markoapps.aisummerizer.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NoteSummary(
    val id: String,
    val tldr: String,
    val summary: String,
    val actionItems: List<String>,
    val deadlines: List<String>,
    val tags: List<String>
)