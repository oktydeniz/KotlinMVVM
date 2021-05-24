package com.oktydeniz.kotlincountries.util

import android.content.Context
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oktydeniz.kotlincountries.R

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_launcher_round)
    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)
}

fun placeHolderProgressBar(c: Context): CircularProgressDrawable {
    return CircularProgressDrawable(c).apply {
        strokeWidth = 8f
        centerRadius = 48f
        start()
    }
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(v: ImageView, url: String?) {
    v.downloadFromUrl(url, placeHolderProgressBar(v.context))
}