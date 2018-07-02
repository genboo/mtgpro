package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sets.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.view.adapter.RecyclerViewAdapter
import ru.spcm.apps.mtgpro.view.adapter.SetsListAdapter
import ru.spcm.apps.mtgpro.view.adapter.diffs.SetsDiffCallback
import ru.spcm.apps.mtgpro.viewmodel.SetsViewModel
import javax.inject.Inject

class SetsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val setAdapterClickListener = object : RecyclerViewAdapter.OnItemClickListener<Set> {
        override fun click(position: Int, item: Set, view: View?) {
            navigator.goToSpoilers(item.code, item.name)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sets, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(SetsViewModel::class.java)
        viewModel.getSets().observe(this, Observer { observeSets(it) })

        val adapter = SetsListAdapter(null)
        adapter.setOnItemClickListener(setAdapterClickListener)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
        list.postDelayed({ viewModel.loadSets() }, 200)

        swipeRefresh.setOnRefreshListener { viewModel.loadSets() }
    }

    private fun observeSets(data: Resource<List<Set>>?) {
        if (data?.data != null) {
            val adapter = (list.adapter as SetsListAdapter)
            val diffs = DiffUtil.calculateDiff(SetsDiffCallback(adapter.getItems(), data.data), !adapter.getItems().isEmpty())
            adapter.setItems(data.data)
            diffs.dispatchUpdatesTo(adapter)
            swipeRefresh.isRefreshing = false
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return ""
    }

}
