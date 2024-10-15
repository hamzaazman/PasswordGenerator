package com.hamzaazman.passwordgenerator.data.repository

import com.hamzaazman.passwordgenerator.data.model.HistoryEntity
import com.hamzaazman.passwordgenerator.data.source.local.MainDao
import com.hamzaazman.passwordgenerator.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val mainDao: MainDao,
) : MainRepository {
    override suspend fun insertHistory(historyEntity: HistoryEntity) {
        // val isPasswordExists = mainDao.isPasswordExists(historyEntity.password)

        mainDao.insert(historyEntity)
    }

    override suspend fun deleteByIdHistory(id: Int) {
        mainDao.deleteById(id)
    }

    override suspend fun getAllHistory(): Flow<List<HistoryEntity>> = flow {
        emit(mainDao.getAll())
    }

    override suspend fun deleteAllHistory() {
        return mainDao.deleteAll()
    }


}