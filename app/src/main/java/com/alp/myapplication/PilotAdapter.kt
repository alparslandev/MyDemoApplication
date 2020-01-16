package com.alp.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.alp.myapplication.network.model.Pilot
import kotlinx.android.synthetic.main.item_pilot.view.*

class PilotAdapter(private var pilots: ArrayList<Pilot>, private val listener: MainListener<Pilot>)
        : RecyclerView.Adapter<GenericViewHolder>() {

    override fun onBindViewHolder(holder: GenericViewHolder, position: Int) {
        val model = pilots[position]

        holder.itemView.iv_fav.isActivated = model.isFavourite
        holder.itemView.tv_pilot_name.text = model.name
        holder.itemView.tv_pilot_point.text = model.point.toString()
        holder.itemView.setOnClickListener {
            listener.onClick(model)
        }
        holder.itemView.iv_fav.setOnClickListener {
            listener.onClickFav(item = model)
        }
    }

    fun updateItem(pilot: Pilot) {
        val index = pilots.indexOf(pilot)
        pilots[index] = pilot
        notifyItemChanged(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder {
        return GenericViewHolder(parent.inflate(R.layout.item_pilot, false))
    }

    override fun getItemCount() = pilots.size
}

interface MainListener <T> {
    fun onClick(item: T) // Generic onclick Listener. If you want only one listener no copy paste lots of interfaces
    fun onClickFav(item: T) // This shoud not be here.
}

class GenericViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) // No copy paste empty viewholder classes

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}