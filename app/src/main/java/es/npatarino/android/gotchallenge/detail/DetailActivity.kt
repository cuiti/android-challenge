package es.npatarino.android.gotchallenge.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import es.npatarino.android.gotchallenge.R
import es.npatarino.android.gotchallenge.extensions.loadUrl
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val d = intent.getStringExtra("description")
        val n = intent.getStringExtra("name")
        val i = intent.getStringExtra("imageUrl")

        detailToolbar.title = n
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailImage.loadUrl(i)
        detailName.text = n
        detailDescription.text = d
    }

    companion object {
        private const val TAG = "DetailActivity"
    }
}