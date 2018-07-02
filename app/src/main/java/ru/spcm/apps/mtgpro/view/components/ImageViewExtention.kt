package ru.spcm.apps.mtgpro.view.components

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import ru.spcm.apps.mtgpro.R
import java.lang.Exception

fun ImageView.loadImageFromCache(image: String){
    val imageView = this
    //Принудительная загрузка из кэша
    Picasso.get()
            .load(image)
            .networkPolicy(NetworkPolicy.OFFLINE)
            .placeholder(R.drawable.pic_card_back)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    //not used
                }

                override fun onError(ex: Exception) {
                    //Если в кэше все-таки нет, загружаем из сети
                    imageView.loadImage(image)
                }
            })
}

fun ImageView.loadImage(image:String){
    Picasso.get()
            .load(image)
            .placeholder(R.drawable.pic_card_back)
            .into(this)
}