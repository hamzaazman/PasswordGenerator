package com.hamzaazman.passwordgenerator.domain.repository

import com.hamzaazman.passwordgenerator.data.model.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    suspend fun insertHistory(historyEntity: HistoryEntity)
    suspend fun deleteByIdHistory(id: Int)
    suspend fun getAllHistory(): Flow<List<HistoryEntity>>
    suspend fun deleteAllHistory()

}