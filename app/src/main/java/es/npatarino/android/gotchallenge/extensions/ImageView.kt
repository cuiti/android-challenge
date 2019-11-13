package es.npatarino.android.gotchallenge.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import es.npatarino.android.gotchallenge.R

fun ImageView.loadUrl(url: String){
    Glide.with(context)
            //.load(url)
            .load("https://www.hbo.com/content/dam/hbodata/series/game-of-thrones/episodes/8/key-art/got-s8-ka-1920x1080.jpg/_jcr_content/renditions/cq5dam.web.1200.675.jpeg")
            .transition(withCrossFade())
            .error(R.drawable.default_image)
            .into(this)
}