package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.layout_app_bar_search.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.view.adapter.WishListAdapter
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.model.tools.Status
import ru.spcm.apps.mtgpro.viewmodel.SearchViewModel


class SearchFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, SearchViewModel::class.java)
        viewModel.getCards().observe(this, Observer { observeSearch(it) })

        val adapter = WishListAdapter(null)
        list.layoutManager = GridLayoutManager(context, 2)
        list.adapter = adapter
        adapter.setOnItemClickListener { _, item, _ -> navigator.goToCard(item.id) }

        searchText.setOnEditorActionListener { view, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                viewModel.search((view as EditText).text.toString())
                handled = true
            }
            return@setOnEditorActionListener handled
        }
    }

    private fun observeSearch(data: Resource<List<Card>>?) {
        if (data != null) {
            if (data.status == Status.SUCCESS && data.data != null) {
                val adapter = list.adapter as WishListAdapter
                adapter.setItems(data.data)
            }
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return ""
    }

}
