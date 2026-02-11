package com.markoapps.aisummerizer.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "summaries")
data class SummaryEntity(
    @PrimaryKey val id: String,
    val noteHash: String,
    val mode: String,

    val tldr: String,
    val summary: String,

    val actionItemsJson: String,
    val deadlinesJson: String,
    val tagsJson: String,

    val createdAt: Long
)