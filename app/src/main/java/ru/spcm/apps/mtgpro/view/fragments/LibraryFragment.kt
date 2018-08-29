package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_library.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryData
import ru.spcm.apps.mtgpro.view.adapter.CardsLibraryListAdapter
import ru.spcm.apps.mtgpro.viewmodel.LibraryViewModel

/**
 * Колода
 * Created by gen on 29.06.2018.
 */

class LibraryFragment : BaseFragment() {

    lateinit var card: Card

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

        val viewModel = getViewModel(this, LibraryViewModel::class.java)
        viewModel.cards.observe(this, Observer { viewModel.setCards(it ?: arrayListOf()) })
        viewModel.mana.observe(this, Observer { viewModel.setManaState(it ?: arrayListOf()) })
        viewModel.colors.observe(this, Observer { viewModel.setColorState(it ?: arrayListOf()) })
        viewModel.library.observe(this, Observer { observeLibrary(it) })

        viewModel.data.observe(this, Observer { observeData(it) })

        viewModel.loadCards(args.getLong(ARG_ID), getSettings().getCurrentValute())

        val adapter = CardsLibraryListAdapter(null)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter

        adapter.setOnItemClickListener { _, item, _ ->
            navigator.goToCard(item.data?.card?.id ?: "")
        }
    }

    private fun observeData(data: LibraryData?) {
        if (data != null && data.isFull()) {
            (list.adapter as CardsLibraryListAdapter).setData(data)
        }
    }

    private fun observeLibrary(data: Library?) {
        if (data != null) {
            updateTitle(data.name)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_delete) {
            val viewModel = getViewModel(this, LibraryViewModel::class.java)
            viewModel.delete(args.getLong(ARG_ID))
            navigator.backTo()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.library_menu, menu)
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

