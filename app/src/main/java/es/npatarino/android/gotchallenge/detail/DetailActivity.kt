package es.npatarino.android.gotchallenge.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.extensions.loadUrl
import es.npatarino.android.gotchallenge.model.Character
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val character = (intent.getSerializableExtra("character") as Character)

        detailToolbar.title = character.name
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = character.name

        detailImage.loadUrl(character.imageUrl)
        detailDescription.text = character.description
    }
}