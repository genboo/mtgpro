package ru.spcm.apps.mtgpro.view

import android.arch.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sets.*
import ru.spcm.apps.mtgpro.R
import javax.inject.Inject

class SetsFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sets, container, false)
        initFragment()
        setHasOptionsMenu(true)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        progressBar = progressBlock
        content = list

    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Сеты"
    }

}
