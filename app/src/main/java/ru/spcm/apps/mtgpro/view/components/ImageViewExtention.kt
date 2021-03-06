package ru.spcm.apps.mtgpro.view.components

import android.graphics.drawable.Drawable
import android.os.Handler
import android.widget.ImageView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.tools.GlideApp

/**
 * Принудительная загрузка изображений из кэша
 */

val requestOptions = RequestOptions
        .placeholderOf(R.drawable.pic_card_back)
        .override(Target.SIZE_ORIGINAL)
        .dontTransform()

fun ImageView.loadImageFromCache(image: String) {
    val imageView = this
    if (image.isEmpty()) {
        GlideApp
                .with(context)
                .load(R.drawable.pic_card_back)
                .apply(requestOptions)
                .into(imageView)
    } else {
        //Принудительная загрузка из кэша
        val handler = Handler()
        GlideApp
                .with(context)
                .load(image)
                .apply(requestOptions)
                .onlyRetrieveFromCache(true)
                .transition(DrawableTransitionOptions.withCrossFade())
                .dontAnimate()
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        handler.post { imageView.loadImage(image) }
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                })
                .into(imageView)
    }
}

/**
 * Загрузка изображений
 */
fun ImageView.loadImage(image: String) {
    if (image.isEmpty()) {
        GlideApp
                .with(context)
                .load(R.drawable.pic_card_back)
                .apply(requestOptions)
                .into(this)
    } else {
        GlideApp
                .with(context)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(this)
    }
}