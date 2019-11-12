package es.npatarino.android.gotchallenge.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.extensions.*
import es.npatarino.android.gotchallenge.housemembers.HouseMembersActivity
import es.npatarino.android.gotchallenge.model.House
import kotlinx.android.synthetic.main.got_house_row.view.*

class GoTHouseAdapter : RecyclerView.Adapter<GoTHouseAdapter.GotCharacterViewHolder>() {

    private val houses: MutableList<House> = ArrayList()

    internal fun addAll(houses: List<House>) {
        this.houses.addAll(houses)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):GotCharacterViewHolder {
        return GotCharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.got_house_row, parent, false))
    }

    override fun onBindViewHolder(holder: GotCharacterViewHolder, position: Int) {
        holder.render(houses[position])
    }

    override fun getItemCount(): Int {
        return houses.size
    }

    inner class GotCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun render(goTHouse: House) {
            with (itemView){
                houseItemImage.loadUrl(goTHouse.imageUrl)
                houseItemName.text =  goTHouse.name
                houseItemImage.setOnClickListener { onHouseClick(goTHouse) }
            }
        }

        private fun onHouseClick(house: House){
            val intent = Intent(itemView.context, HouseMembersActivity::class.java)
            intent.putExtra("house", house)
            itemView.context.startActivity(intent)
        }
    }

}