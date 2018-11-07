package com.hamster.mojo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.Serializable

@Entity
data class Hamster(val title: String, val description: String, val image: String?, val pinned: Boolean) : Serializable {
    @PrimaryKey
    var uid: Int? = null
}


@BindingAdapter("pictureUrl")
fun loadImage(view: ImageView, url: String?) {
    Picasso.get().load(url).into(view)
}
