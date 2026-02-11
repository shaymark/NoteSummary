package com.markoapps.aisummerizer.data.local

import com.markoapps.aisummerizer.data.local.dao.SummaryDao
import com.markoapps.aisummerizer.data.local.mapper.SummaryMapper
import com.markoapps.aisummerizer.domain.model.NoteSummary
import kotlin.collections.map

class AiLocalDataSource(
    private val dao: SummaryDao,
    private val mapper: SummaryMapper
) {

    suspend fun save(noteHash: String, mode: String, summary: NoteSummary): String {
        val entity = mapper.toEntity(noteHash, mode, summary)
        dao.insert(entity)
        return entity.id
    }

    suspend fun getCached(noteHash: String, mode: String): NoteSummary? {
        val entity = dao.getByHash(noteHash, mode) ?: return null
        return mapper.fromEntity(entity)
    }

    suspend fun getHistory(): List<NoteSummary> {
        return dao.getAll().map { mapper.fromEntity(it) }
    }

    suspend fun getSummaryById(id: String): NoteSummary? {
        val entity = dao.getById(id)
        return entity?.let { mapper.fromEntity(it) }
    }
}