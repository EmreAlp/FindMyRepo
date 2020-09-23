package com.ing.findmyrepo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingUtils {

    companion object {

        @JvmStatic
        @BindingAdapter("image")
        fun loadImage(view: ImageView, imageUrl: String?) {

            imageUrl?.let {
                Glide.with(view)
                    .load(imageUrl)
                    .into(view)
            }
        }
    }
}