package es.npatarino.android.gotchallenge.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import es.npatarino.android.gotchallenge.R

fun ImageView.loadUrl(url: String){
    Glide.with(context)
            .load(url)
            .transition(withCrossFade())
            .error(R.drawable.default_image)
            .into(this)
}