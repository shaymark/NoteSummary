package com.markoapps.aisummerizer.data.local.mapper

import com.markoapps.aisummerizer.data.local.JsonListConverter
import com.markoapps.aisummerizer.data.local.entity.SummaryEntity
import com.markoapps.aisummerizer.domain.model.NoteSummary

class SummaryMapper(
    private val converter: JsonListConverter
) {
    fun toEntity(
        noteHash: String,
        mode: String,
        summary: NoteSummary
    ): SummaryEntity {
        return SummaryEntity(
            id = "$noteHash-$mode",
            noteHash = noteHash,
            mode = mode,
            tldr = summary.tldr,
            summary = summary.summary,
            actionItemsJson = converter.toJson(summary.actionItems),
            deadlinesJson = converter.toJson(summary.deadlines),
            tagsJson = converter.toJson(summary.tags),
            createdAt = System.currentTimeMillis()
        )
    }

    fun fromEntity(entity: SummaryEntity): NoteSummary {
        return NoteSummary(
            id = entity.id,
            tldr = entity.tldr,
            summary = entity.summary,
            actionItems = converter.fromJson(entity.actionItemsJson),
            deadlines = converter.fromJson(entity.deadlinesJson),
            tags = converter.fromJson(entity.tagsJson)
        )
    }
}