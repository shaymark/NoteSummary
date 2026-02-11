package com.markoapps.aisummerizer.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.markoapps.aisummerizer.data.local.dao.SummaryDao
import com.markoapps.aisummerizer.data.local.entity.SummaryEntity

@Database(
    entities = [SummaryEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun summaryDao(): SummaryDao
}