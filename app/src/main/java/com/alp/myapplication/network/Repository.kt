package com.peakup.leave.network

import com.alp.myapplication.network.Api
import com.alp.myapplication.network.model.PilotInfo
import com.alp.myapplication.network.networkCall
import com.alp.myapplication.network.response.PilotResponse


object Repository {

    fun pilots() = networkCall<PilotResponse> {
        client = Api.dataClient.getPilots()
    }

    fun pilotById (pilotId: Int) = networkCall<PilotInfo> {
        client = Api.dataClient.getPilotById(pilotId)
    }
}