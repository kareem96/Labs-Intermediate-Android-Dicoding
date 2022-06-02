package com.crm.siska.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.File

object GlideHelper {
    fun setImageFile(context: Context, urlImage: File?, imageView: ImageView?){
        Glide.with(context)
            .load(urlImage)
            .into(imageView as ImageView)
    }
    fun setImage(context: Context, urlImage:Int, imageView: ImageView?){
        Glide.with(context)
            .load(urlImage)
            .into(imageView as ImageView)
    }
    fun setImageUrl(context: Context, urlImage:String, imageView: ImageView?){
        Glide.with(context)
            .load(urlImage)
            .into(imageView as ImageView)
    }


}