package ru.spcm.apps.mtgpro.view.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class RecyclerViewScrollListener(private val load: (Int) -> Unit,
                                 private val pageSize: Int) : RecyclerView.OnScrollListener() {

    private var loading = false
    private var previousTotal = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = recyclerView.layoutManager?.childCount ?: 0
        val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0 - 1
        val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

        if (previousTotal == 0) {
            loading = true
        }

        if (loading && totalItemCount > previousTotal) {
            loading = false
            previousTotal = totalItemCount
        }

        if (!loading && totalItemCount - (visibleItemCount + firstVisibleItem) < DEFAULT_REACT) {
            loading = true
            if ((totalItemCount - 1) % pageSize == 0) {
                load(totalItemCount - 1 + pageSize)
            }
        }
    }

    companion object {
        const val DEFAULT_REACT = 15
    }
}

