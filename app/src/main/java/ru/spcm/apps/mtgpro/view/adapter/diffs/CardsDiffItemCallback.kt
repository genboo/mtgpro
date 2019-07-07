package ru.spcm.apps.mtgpro.view.adapter.diffs

import androidx.recyclerview.widget.DiffUtil
import ru.spcm.apps.mtgpro.model.dto.CardCollection

object CardsDiffItemCallback : DiffUtil.ItemCallback<CardCollection>() {
    override fun areContentsTheSame(oldItem: CardCollection, newItem: CardCollection): Boolean {
        return oldItem.card.name == newItem.card.name
                && oldItem.card.set == newItem.card.set
                && oldItem.card.count == newItem.card.count
                && oldItem.price == newItem.price
    }

    override fun areItemsTheSame(oldItem: CardCollection, newItem: CardCollection): Boolean {
        return oldItem.card.id == newItem.card.id
    }
}