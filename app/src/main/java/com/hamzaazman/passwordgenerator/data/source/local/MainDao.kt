package com.hamzaazman.passwordgenerator.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hamzaazman.passwordgenerator.data.model.HistoryEntity

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(historyEntity: HistoryEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM history WHERE password = :password)")
    suspend fun isPasswordExists(password: String): Boolean

    @Query("SELECT * FROM history ORDER BY id")
    suspend fun getAll(): List<HistoryEntity>

    @Query("SELECT * FROM history WHERE id = :id")
    suspend fun getById(id: Int): HistoryEntity

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun deleteById(id: Int)

}