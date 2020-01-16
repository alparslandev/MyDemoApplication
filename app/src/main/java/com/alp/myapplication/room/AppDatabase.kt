package com.alp.myapplication.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alp.myapplication.network.model.Pilot

@Database(entities = arrayOf(Pilot::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pilotDao(): PilotDao
}