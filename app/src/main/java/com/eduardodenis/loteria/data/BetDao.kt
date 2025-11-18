package com.eduardodenis.loteria.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BetDao {

    @Insert
    fun insert(bet: Bet)

    @Query("SELECT * FROM bets WHERE type = :betType")
    fun getNumberByType(betType: String): List<Bet>
}