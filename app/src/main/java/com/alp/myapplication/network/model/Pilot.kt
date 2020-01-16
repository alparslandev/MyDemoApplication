package com.alp.myapplication.network.model

import java.io.Serializable

data class Pilot(
    val id: Int,
    val name: String,
    val point: Int
): Serializable

data class PilotInfo(
    val name: String,
    val age: Int,
    val team: String,
    val image: String,
    var id: Int
): Serializable