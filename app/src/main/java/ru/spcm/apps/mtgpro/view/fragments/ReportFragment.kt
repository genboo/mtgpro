package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_report.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardObserved
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.view.adapter.ReportListAdapter
import ru.spcm.apps.mtgpro.viewmodel.ReportViewModel

class ReportFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        initFragment()
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()
        val viewModel = getViewModel(this, ReportViewModel::class.java)
        viewModel.getReport().observe(this, Observer { observeReport(it) })
        viewModel.loadReport(getSettings().getCurrentValute())
        val adapter = ReportListAdapter(null)
        list.layoutManager = GridLayoutManager(requireContext(), 3)
        list.adapter = adapter

        adapter.setOnItemClickListener { _, item, _ -> navigator.goToPriceVolatility(item.id) }
    }

    private fun observeReport(data: List<CardObserved>?) {
        if (data != null) {
            (list.adapter as ReportListAdapter).setItems(data)
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Отчет"
    }

}
