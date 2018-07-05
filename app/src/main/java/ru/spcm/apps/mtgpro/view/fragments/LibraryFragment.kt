package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_library.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardForLibrary
import ru.spcm.apps.mtgpro.view.adapter.CardsLibraryListAdapter
import ru.spcm.apps.mtgpro.viewmodel.LibraryViewModel
import javax.inject.Inject

/**
 * Карта
 * Created by gen on 29.06.2018.
 */

class LibraryFragment : BaseFragment() {

    lateinit var card: Card

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        initFragment()
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(LibraryViewModel::class.java)
        viewModel.cards.observe(this, Observer { observeCards(it) })
        viewModel.loadCards(args.getLong(ARG_ID))

        val adapter = CardsLibraryListAdapter(null)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
    }

    private fun observeCards(data: List<CardForLibrary>?) {
        if (data != null) {
            (list.adapter as CardsLibraryListAdapter).setCards(data)
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return ""
    }

    companion object {

        private const val ARG_ID = "id"

        fun getInstance(id: Long): LibraryFragment {
            val args = Bundle()
            args.putLong(ARG_ID, id)
            val fragment = LibraryFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

