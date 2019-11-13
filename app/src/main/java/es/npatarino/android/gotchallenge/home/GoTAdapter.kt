package es.npatarino.android.gotchallenge.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.detail.DetailActivity
import es.npatarino.android.gotchallenge.extensions.*
import es.npatarino.android.gotchallenge.model.Character
import kotlinx.android.synthetic.main.got_character_row.view.*
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair



class GoTAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val characters: MutableList<Character> = ArrayList()

    fun addAll(characters: List<Character>) {
        this.characters.addAll(characters)
        notifyDataSetChanged()
    }

    fun replace(characters: List<Character>){
        this.characters.clear()
        addAll(characters)
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
                characterItemImage.setOnClickListener { onCharacterClick(goTCharacter) }
                characterItemName.transitionName = goTCharacter.name
                characterItemImage.transitionName = goTCharacter.name + goTCharacter.imageUrl
            }
        }

        private fun onCharacterClick(character: Character){
            val sharedElements = arrayOf<Pair<View,String>>(Pair(itemView, "detailToolbarImage"),
                                        Pair(itemView.characterItemName, "detailToolbarTitle"))
            val options = ActivityOptionsCompat
                    .makeSceneTransitionAnimation(itemView.context as Activity, *sharedElements)
            val intent = Intent(itemView.context, DetailActivity::class.java)
            intent.putExtra("character", character)
            itemView.context.startActivity(intent, options.toBundle())
        }
    }

}
