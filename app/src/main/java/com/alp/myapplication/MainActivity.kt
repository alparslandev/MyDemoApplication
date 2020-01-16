package com.alp.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.room.Room
import com.alp.myapplication.network.Resource
import com.alp.myapplication.network.model.Pilot
import com.alp.myapplication.room.AppDatabase
import com.peakup.leave.network.Repository
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var pilotAdapter: PilotAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "FavouritePilots"
        ).build()

        fetchPilots()
    }

    private fun fetchPilots() {
        // TODO showLoading()
        Repository.pilots().observe(this, Observer{
            when(it.status) {
                Resource.SUCCESS -> {
                    it.data?.let { response ->
                        response.items.map { pilotItem ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val pilot =  db.pilotDao().loadById(pilotId = pilotItem.id)
                                pilotItem.isFavourite = pilot != null
                                pilot?.let { pilotNoNull ->

                                }
                            }
                        }

                        if (!::pilotAdapter.isInitialized) {
                            pilotAdapter = PilotAdapter(
                                ArrayList(response.items),
                                object : MainListener<Pilot> {
                                    override fun onClick(item: Pilot) {
                                        redirectPilotDetails(item)
                                    }

                                    override fun onClickFav(item: Pilot) {
                                        favPilot(item)
                                    }
                                })
                            rv_pilots.adapter = pilotAdapter
                        } // else update items
                    }
                }
                Resource.ERROR -> {
                    // TODO handleError(it.throwable)
                }
            }
        })
    }

    private fun favPilot(item: Pilot) {
        CoroutineScope(Dispatchers.IO).launch {
            val pilot =  db.pilotDao().loadById(pilotId = item.id)
            item.isFavourite = !item.isFavourite
            withContext(Dispatchers.Main) {
                pilotAdapter.updateItem(item)
            }

            pilot?.let {
                db.pilotDao().delete(item)
            } ?: kotlin.run {
                db.pilotDao().insertAll(item)
            }
        }
    }

    private fun redirectPilotDetails(item: Pilot) {
        val intent = Intent(this@MainActivity, PilotDetailsActivity::class.java)
        intent.putExtra(PilotDetailsActivity.EXTRA_PILOT, item)
        startActivity(intent)
    }
}
