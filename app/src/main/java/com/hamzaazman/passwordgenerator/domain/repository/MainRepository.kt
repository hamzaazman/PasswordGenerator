package com.hamzaazman.passwordgenerator.domain.repository

import com.hamzaazman.passwordgenerator.data.model.HistoryEntity

interface MainRepository {
    suspend fun insertHistory(historyEntity: HistoryEntity)
    suspend fun deleteByIdHistory(id: Int)
    suspend fun getAllHistory(): List<HistoryEntity>

}