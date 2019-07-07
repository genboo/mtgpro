package ru.spcm.apps.mtgpro.view.adapter.diffs

import androidx.recyclerview.widget.DiffUtil
import ru.spcm.apps.mtgpro.model.dto.CardWatched

object CardsWatchedDiffItemCallback : DiffUtil.ItemCallback<CardWatched>() {
    override fun areContentsTheSame(oldItem: CardWatched, newItem: CardWatched): Boolean {
        return oldItem.price == newItem.price
    }

    override fun areItemsTheSame(oldItem: CardWatched, newItem: CardWatched): Boolean {
        return oldItem.card.id == newItem.card.id
    }
}