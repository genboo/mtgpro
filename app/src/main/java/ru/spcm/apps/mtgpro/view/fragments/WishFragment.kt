package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_wish.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.adapter.WishListAdapter
import ru.spcm.apps.mtgpro.viewmodel.WishViewModel

class WishFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wish, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, WishViewModel::class.java)
        viewModel.getCards().observe(this, Observer { observeCards(it) })

        val adapter = WishListAdapter(null)
        list.layoutManager = GridLayoutManager(context, 3)
        list.adapter = adapter
        adapter.setOnItemClickListener { _, item, _ -> navigator.goToCard(item.id) }

        list.postDelayed({ viewModel.loadWishedCards() }, 200)
    }

    private fun observeCards(data: List<Card>?) {
        if (data != null) {
            val adapter = list.adapter as WishListAdapter
            adapter.setItems(data)
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Хочу!"
    }

}
