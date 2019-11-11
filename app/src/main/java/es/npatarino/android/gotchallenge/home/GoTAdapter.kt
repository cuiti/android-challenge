package es.npatarino.android.gotchallenge.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.detail.DetailActivity
import es.npatarino.android.gotchallenge.extensions.*
import es.npatarino.android.gotchallenge.model.Character
import kotlinx.android.synthetic.main.got_character_row.view.*

class GoTAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val characters: MutableList<Character> = ArrayList()

    internal fun addAll(characters: List<Character>) {
        this.characters.addAll(characters)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GotCharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.got_character_row, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val gotCharacterViewHolder = holder as GotCharacterViewHolder
        gotCharacterViewHolder.render(characters[position])
    }

    override fun getItemCount() = characters.size

    internal inner class GotCharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun render(goTCharacter: Character) {
            with(itemView) {
                characterItemImage.loadUrl(goTCharacter.imageUrl)
                characterItemName.text = goTCharacter.name
                characterItemImage.setOnClickListener { onCharacterClick() }
            }
        }

        private fun onCharacterClick(){
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra("description", characters[position].description)
            intent.putExtra("name", characters[position].name)
            intent.putExtra("imageUrl", characters[position].imageUrl)
            itemView.context.startActivity(intent)
        }
    }

}
