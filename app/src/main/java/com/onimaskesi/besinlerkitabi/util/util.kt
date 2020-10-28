package com.onimaskesi.besinlerkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.onimaskesi.besinlerkitabi.R

fun ImageView.gorselIndir(url : String? , placeholder: CircularProgressDrawable){

    val options = RequestOptions().placeholder(placeholder)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)

}

fun placeholderYap(context : Context) : CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }

}

@BindingAdapter("android:downloadImage")
fun downloadImage(imageView: ImageView , url : String?){
    imageView.gorselIndir(url, placeholderYap(imageView.context))
}