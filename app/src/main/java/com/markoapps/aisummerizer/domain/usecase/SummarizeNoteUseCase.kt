package com.markoapps.aisummerizer.domain.usecase

import com.markoapps.aisummerizer.data.repository.AiRepository
import com.markoapps.aisummerizer.domain.model.NoteSummary
import com.markoapps.aisummerizer.domain.model.SummaryMode

class SummarizeNoteUseCase(
    private val repository: AiRepository
) {
    suspend operator fun invoke(note: String, mode: SummaryMode): NoteSummary {
        return repository.summarize(note, mode)
    }
}