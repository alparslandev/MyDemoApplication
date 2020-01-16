package com.alp.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.alp.myapplication.network.Resource
import com.peakup.leave.network.Repository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }
    private fun fetchPilots() {
        //showLoading()
        Repository.pilots().observe(this, Observer{
            when(it.status) {
                Resource.SUCCESS -> {
                    it.data?.let { pilots ->
                        val pilot = pilots.items[1]
                    }
                }
                Resource.ERROR -> {
                    // TODO handleError(it.throwable)
                }
            }
        })
    }
}
