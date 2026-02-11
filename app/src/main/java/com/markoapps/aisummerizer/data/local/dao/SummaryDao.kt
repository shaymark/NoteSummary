package com.markoapps.aisummerizer.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.markoapps.aisummerizer.data.local.entity.SummaryEntity

@Dao
interface SummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SummaryEntity)

    @Query("SELECT * FROM summaries WHERE noteHash = :noteHash AND mode = :mode LIMIT 1")
    suspend fun getByHash(noteHash: String, mode: String): SummaryEntity?

    @Query("SELECT * FROM summaries WHERE id = :id")
    suspend fun getById(id: String): SummaryEntity?


    @Query("SELECT * FROM summaries ORDER BY createdAt DESC")
    suspend fun getAll(): List<SummaryEntity>

    @Query("DELETE FROM summaries")
    suspend fun clearAll()
}