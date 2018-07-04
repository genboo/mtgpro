package ru.spcm.apps.mtgpro.view.adapter.diffs

import android.support.v7.util.DiffUtil
import ru.spcm.apps.mtgpro.model.dto.Card

object CardsDiffItemCallback : DiffUtil.ItemCallback<Card>() {
    override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.name == newItem.name
                && oldItem.set == newItem.set
                && oldItem.count == newItem.count
    }

    override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
        return oldItem.id == newItem.id
    }
}