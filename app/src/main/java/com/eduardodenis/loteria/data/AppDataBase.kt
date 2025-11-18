package com.eduardodenis.loteria.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(Converters::class)
@Database(entities = [Bet::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun betDao(): BetDao

    companion object {

        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                buildDatabase(context).also {
                    instance = it
                    Log.i("create", "Banco criado")
                }
            }
        }

        private fun buildDatabase(context: Context): AppDataBase {
            return Room.databaseBuilder(
                context,
                AppDataBase::class.java,
                "loteria_app"
            ).build()
        }
    }
}