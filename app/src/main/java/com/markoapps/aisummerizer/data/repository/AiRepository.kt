package com.markoapps.aisummerizer.data.repository

import com.markoapps.aisummerizer.data.local.AiLocalDataSource
import com.markoapps.aisummerizer.data.remote.OpenAiRemoteDataSource
import com.markoapps.aisummerizer.data.remote.model.toNoteSummary
import com.markoapps.aisummerizer.data.remote.parser.SummaryParser
import com.markoapps.aisummerizer.domain.model.NoteSummary
import com.markoapps.aisummerizer.domain.model.SummaryMode
import com.markoapps.aisummerizer.util.HashUtil

class AiRepository(
    private val remote: OpenAiRemoteDataSource,
    private val local: AiLocalDataSource,
    private val parser: SummaryParser
) {
    suspend fun summarize(note: String, mode: SummaryMode): NoteSummary {
        val noteHash = HashUtil.sha256(note)

        val cached = local.getCached(noteHash, mode.name)
        if (cached != null) return cached

        val json = remote.summarize(note, mode)
        val summary = parser.parse(json).toNoteSummary()
        val id = local.save(noteHash, mode.name, summary)

        return summary.copy(id = id)
    }
}