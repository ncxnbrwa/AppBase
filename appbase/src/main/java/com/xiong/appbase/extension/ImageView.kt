package com.xiong.appbase.extension

import android.graphics.Bitmap
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.xiong.appbase.R
import com.xiong.appbase.base.BaseApplication
import com.xiong.appbase.utils.MyUtils
import jp.wasabeef.glide.transformations.ColorFilterTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.File

//加载圆形图片
fun ImageView.loadImageCircle(url: String) {
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).asBitmap().load(url).apply(RequestOptions.centerCropTransform())
            .into(object : BitmapImageViewTarget(this) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource)
                    circularBitmapDrawable.isCircular = true
                    setImageDrawable(circularBitmapDrawable)
                }
            })
}

fun ImageView.loadImageRoundedCorners(url: String, radius: Int, margin: Int, cornerType: RoundedCornersTransformation.CornerType) {
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).load(url)
            .apply(RequestOptions().placeholder(R.mipmap.zhanweitu))
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius, margin, cornerType)))
            .into(this)
}

fun ImageView.loadImageFit(url: String) {
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).load(url)
            .apply(RequestOptions().placeholder(R.mipmap.zhanweitu))
            .into(this)
}

fun ImageView.loadAvatarFit(url: String) {
    //加载头像
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).load(url)
            .apply(RequestOptions().placeholder(R.mipmap.avatar))
            .into(this)
}

fun ImageView.loadImageWithoutHolder(url: String) {
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).load(url)
            .apply(RequestOptions().centerCrop())
            .into(this)
}

fun ImageView.loadImageFilter(url: String, color: Int) {
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).load(url)
            .apply(RequestOptions().placeholder(R.mipmap.zhanweitu).centerCrop())
            .apply(RequestOptions.bitmapTransform(ColorFilterTransformation(color)))
            .into(this)
}

//加载本地图片
fun ImageView.loadLocalImage(file: File) {
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).load(file)
            .apply(RequestOptions()).into(this)
}

//加载资源文件
fun ImageView.loadResources(resources: Int) {
    if (!MyUtils.isOnMainThread()) return
    Glide.with(BaseApplication.getAppContext()).load(resources)
            .apply(RequestOptions()).into(this)
}