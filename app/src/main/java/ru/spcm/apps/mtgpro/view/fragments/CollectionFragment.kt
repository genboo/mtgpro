package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_collection.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.repository.CollectionRepo
import ru.spcm.apps.mtgpro.repository.bounds.SpoilersBound
import ru.spcm.apps.mtgpro.view.adapter.CardsListAdapter
import ru.spcm.apps.mtgpro.view.adapter.RecyclerViewScrollListener
import ru.spcm.apps.mtgpro.viewmodel.CollectionViewModel
import javax.inject.Inject

class CollectionFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CollectionViewModel::class.java)
        viewModel.getCards().observe(this, Observer { observeCards(it) })

        val adapter = CardsListAdapter(null)
        list.layoutManager = LinearLayoutManager(context)
        list.clearOnScrollListeners()
        list.addOnScrollListener(RecyclerViewScrollListener({ viewModel.loadCards(it) },
                CollectionRepo.PAGES_SIZE, true))
        list.adapter = adapter
        adapter.setOnItemClickListener { _, item, _ -> navigator.goToCard(item.id) }

        list.postDelayed({ viewModel.loadCards(0) }, 200)
    }

    private fun observeCards(data: List<Card>?) {
        if (data != null) {
            val adapter = list.adapter as CardsListAdapter
            adapter.setItems(data)
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return ""
    }

}
