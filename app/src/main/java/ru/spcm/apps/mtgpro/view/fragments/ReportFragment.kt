package ru.spcm.apps.mtgpro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.spcm.apps.mtgpro.R

class ReportFragment : BaseFragment() {

    private val sets = HashMap<String, String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Отчет"
    }

}
