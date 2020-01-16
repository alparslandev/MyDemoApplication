package com.alp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.alp.myapplication.network.Resource
import com.alp.myapplication.network.model.Pilot
import com.peakup.leave.network.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchPilots()
    }

    private fun fetchPilots() {
        // TODO showLoading()
        Repository.pilots().observe(this, Observer{
            when(it.status) {
                Resource.SUCCESS -> {
                    it.data?.let { response ->
                        rv_pilots.adapter = PilotAdapter(ArrayList(response.items), object : MainListener<Pilot> {
                            override fun onClick(item: Pilot) {
                                redirectPilotDetails(item)
                            }
                        })
                    }
                }
                Resource.ERROR -> {
                    // TODO handleError(it.throwable)
                }
            }
        })
    }

    private fun redirectPilotDetails(item: Pilot) {
        val intent = Intent(this@MainActivity, PilotDetailsActivity::class.java)
        intent.putExtra(PilotDetailsActivity.EXTRA_PILOT, item)
        startActivity(intent)
    }
}
