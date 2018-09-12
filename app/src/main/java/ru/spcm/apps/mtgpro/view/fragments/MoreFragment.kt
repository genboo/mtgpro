package ru.spcm.apps.mtgpro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_more.*
import ru.spcm.apps.mtgpro.R

class MoreFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        libraries.setOnClickListener { libraries.postDelayed({ navigator.goToLibraries() }, 100) }
        report.setOnClickListener { report.postDelayed({ navigator.goToReport()}, 100) }
        watch.setOnClickListener { watch.postDelayed({ navigator.goToWatch()}, 100) }
        settings.setOnClickListener { settings.postDelayed({ navigator.goToSettings()}, 100) }

    }

    override fun getTitle(): String {
        return "Еще"
    }

}
