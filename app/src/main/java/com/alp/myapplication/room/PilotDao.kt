package com.alp.myapplication.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alp.myapplication.network.model.Pilot

@Dao
interface PilotDao {
    @Query("SELECT * FROM Pilot")
    fun getAll(): LiveData<List<Pilot>>

    @Query("SELECT * FROM Pilot WHERE id IN (:pilotId)")
    fun loadById(pilotId: Int): Pilot?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg pilots: Pilot)

    @Query("DELETE FROM Pilot WHERE id = :pilotId")
    fun deletePilotWithId(pilotId: Int)

    @Delete
    fun delete(user: Pilot)
}