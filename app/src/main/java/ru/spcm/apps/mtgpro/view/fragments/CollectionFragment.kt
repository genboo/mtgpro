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
import ru.spcm.apps.mtgpro.view.adapter.CardsListAdapter
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

        val adapter = CardsListAdapter()
        adapter.listener = { navigator.goToCard(it.id) }
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CollectionViewModel::class.java)
        viewModel.allCards.observe(this, Observer { adapter.submitList(it) })
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Коллекция"
    }

}
