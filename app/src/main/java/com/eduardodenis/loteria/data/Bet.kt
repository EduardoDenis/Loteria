package com.eduardodenis.loteria.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "bets")
data class Bet(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    val type: String,
    val numbers: String,
    val date: Date = Date()
)
