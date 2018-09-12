package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_collection.*
import kotlinx.android.synthetic.main.layout_filter.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.adapter.CardsListAdapter
import ru.spcm.apps.mtgpro.view.adapter.ExpandableListAdapter
import ru.spcm.apps.mtgpro.model.dto.FilterItem
import ru.spcm.apps.mtgpro.viewmodel.CollectionViewModel

class CollectionFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)
        initFragment()
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val adapter = CardsListAdapter()
        adapter.listener = { navigator.goToCard(it.id) }
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        val viewModel = getViewModel(this, CollectionViewModel::class.java)
        viewModel.cards.observe(this, Observer { adapter.submitList(it) })
        viewModel.filters.observe(this, Observer { observeFilters(it) })

        if(viewModel.cards.value == null) {
            viewModel.loadCards(getSettings().getCurrentValute(), viewModel.selectedFilter)
        }

        filterApply.setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.END)
            adapter.submitList(null)
            viewModel.loadCards(getSettings().getCurrentValute(), (filterList.expandableListAdapter as ExpandableListAdapter).getSelectedItems())
        }
    }

    private fun observeFilters(data: List<FilterItem>?) {
        if(data != null){
            val adapter = ExpandableListAdapter(requireActivity(), data)
            filterList.setAdapter(adapter)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_filter) {
            if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
                drawerLayout.closeDrawer(GravityCompat.END)
            } else {
                drawerLayout.openDrawer(GravityCompat.END)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.collection_menu, menu)
    }

    override fun getTitle(): String {
        return "Коллекция"
    }

}
