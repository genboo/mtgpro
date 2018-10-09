package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sets.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Set
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.view.adapter.SetsListAdapter
import ru.spcm.apps.mtgpro.viewmodel.SetsViewModel

class SetsFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sets, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, SetsViewModel::class.java)
        viewModel.getSets().observe(this, Observer { observeSets(it) })

        val wishArchive = getSettings().getBoolean(Setting.Type.SHOW_SETS_ARCHIVE, false)

        val adapter = SetsListAdapter(null)
        adapter.setOnItemClickListener { _, item, _ -> navigator.goToSpoilers(item.code, item.name) }
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
        list.postDelayed({ viewModel.loadSets(wishArchive, false) }, 200)

        swipeRefresh.setOnRefreshListener { viewModel.loadSets(wishArchive, true) }
    }

    private fun observeSets(data: Resource<List<Set>>?) {
        if (data?.data != null) {
            (list.adapter as SetsListAdapter).setItems(data.data)
            swipeRefresh.isRefreshing = false
        }
    }

    override fun getTitle(): String {
        return "Сеты"
    }

}
