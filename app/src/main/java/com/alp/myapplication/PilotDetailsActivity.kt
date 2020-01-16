package com.alp.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.alp.myapplication.network.Resource
import com.alp.myapplication.network.model.Pilot
import com.alp.myapplication.network.model.PilotInfo
import com.bumptech.glide.Glide
import com.peakup.leave.network.Repository
import kotlinx.android.synthetic.main.activity_pilot_details.*

class PilotDetailsActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PILOT = "EXTRA_PILOT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pilot_details)

        if (intent?.extras?.getSerializable(EXTRA_PILOT) != null &&
            intent?.extras?.getSerializable(EXTRA_PILOT) is Pilot) {
            val pilot = intent?.extras?.getSerializable(EXTRA_PILOT) as Pilot
            pilotDetails(pilot)
        }
    }

    private fun pilotDetails(pilot: Pilot) {
        Repository.pilotById(pilot.id).observe(this, Observer{
            when(it.status) {
                Resource.SUCCESS -> {
                    it.data?.let { response ->
                        updateUIByPilotInfo(pilot, response)
                    }
                }
                Resource.ERROR -> {
                    // TODO handleError(it.throwable)
                }
            }
        })
    }

    private fun updateUIByPilotInfo(pilot: Pilot, pilotInfo: PilotInfo) {
        tv_pilot_age.text = pilotInfo.age.toString()
        tv_pilot_team.text = pilotInfo.team
        Glide.with(this).load(pilotInfo.image).into(iv_pilot)
    }
}