package com.alp.myapplication.network.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Pilot(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "point") val point: Int,
    var isFavourite: Boolean = false
): Serializable

data class PilotInfo(
    val name: String,
    val age: Int,
    val team: String,
    val image: String,
    var id: Int
): Serializable