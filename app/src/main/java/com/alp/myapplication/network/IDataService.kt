package com.alp.myapplication.network

import com.alp.myapplication.network.model.PilotInfo
import com.alp.myapplication.network.response.PilotResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface IDataService {

    @GET("${API}drivers")
    fun getPilots(): Deferred<Response<PilotResponse>>

    @GET("${API}driverDetail/{pilotId}")
    fun getPilotById(@Path("pilotId") pilotId: Int): Deferred<Response<PilotInfo>>
}