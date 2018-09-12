package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_watch.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.adapter.CardsWatchedListAdapter
import ru.spcm.apps.mtgpro.viewmodel.WatchViewModel

class WatchFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_watch, container, false)
        setHasOptionsMenu(true)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, WatchViewModel::class.java)

        val adapter = CardsWatchedListAdapter()
        adapter.listener = { navigator.goToPriceVolatility(it.card.id) }
        adapter.deleteListener = { viewModel.delete(it.card.id) }
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        viewModel.getCards().observe(this, Observer { adapter.submitList(it) })
        if(viewModel.getCards().value == null) {
            viewModel.loadCards(getSettings().getCurrentValute())
        }
    }

    override fun getTitle(): String {
        return "Список отслеживания"
    }

}
