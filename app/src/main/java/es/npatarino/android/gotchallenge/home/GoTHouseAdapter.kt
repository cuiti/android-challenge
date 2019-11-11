package es.npatarino.android.gotchallenge.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.extensions.*
import es.npatarino.android.gotchallenge.model.House

class GoTHouseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val houses: MutableList<House> = ArrayList()

    internal fun addAll(collection: Collection<House>) {
        for (i in collection.indices) {
            houses.add(collection.toTypedArray()[i])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GotCharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.got_house_row, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val gotCharacterViewHolder = holder as GotCharacterViewHolder
        gotCharacterViewHolder.render(houses[position])
    }

    override fun getItemCount(): Int {
        return houses.size
    }

    inner class GotCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imp: ImageView
        var tvn: TextView

        init {
            imp = itemView.findViewById(R.id.houseItemImage)
            tvn = itemView.findViewById(R.id.tv_name)
        }

        fun render(goTHouse: House) {
            tvn.text = goTHouse.name
            imp.loadUrl(goTHouse.imageUrl)
        }
    }

}