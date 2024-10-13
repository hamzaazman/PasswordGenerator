package com.hamzaazman.passwordgenerator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val password: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    override fun toString(): String {
        return "HistoryEntity(id=$id, password='$password', timestamp=$timestamp)"
    }

    fun getFormattedDate(): String {
        val date = Date(timestamp)
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}
