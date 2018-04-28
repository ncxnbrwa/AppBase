package com.xiong.appbase.extension

import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.xiong.appbase.R
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File

//加载圆形图片
fun ImageView.loadImageCircle(url: String) {
    Glide.with(context).asBitmap().load(url).apply(RequestOptions.centerCropTransform())
            .into(object : BitmapImageViewTarget(this) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource)
                    circularBitmapDrawable.isCircular = true
                    setImageDrawable(circularBitmapDrawable)
                }
            })
}

fun ImageView.loadImageRoundedCorners(url: String, radius: Int, margin: Int, cornerType: RoundedCornersTransformation.CornerType) {
    val options = RequestOptions().placeholder(R.mipmap.place_holder)
    Glide.with(context).load(url)
            .apply(options)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius, margin, cornerType)))
            .into(this)
}

fun ImageView.loadImage(url: String) {
    val options = RequestOptions().placeholder(R.mipmap.place_holder).centerCrop()
    Glide.with(context).load(url).apply(options).into(this)
}

fun ImageView.loadImageFit(url: String) {
    val options = RequestOptions().placeholder(R.mipmap.place_holder)
    Glide.with(context).load(url).apply(options).into(this)
}

fun ImageView.loadImageWithoutHolder(url: String) {
    Glide.with(context).load(url).apply(RequestOptions().centerCrop()).into(this)
}

fun ImageView.loadImagePicHolder(url: String) {
    val options = RequestOptions().placeholder(R.mipmap.place_holder).centerCrop()
    Glide.with(context).load(url).apply(options).into(this)
}

fun ImageView.loadImageFliter(url: String, color: Int) {
    val options = RequestOptions().placeholder(R.mipmap.place_holder).centerCrop()
    Glide.with(context).load(url).apply(options)
            .apply(RequestOptions.bitmapTransform(ColorFilterTransformation(color)))
            .into(this)
}

//加载本地图片
fun ImageView.loadLocalImage(file: File) {
    Glide.with(context).load(file).apply(RequestOptions().centerCrop()).into(this)
}