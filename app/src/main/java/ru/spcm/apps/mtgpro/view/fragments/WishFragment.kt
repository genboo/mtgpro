package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import kotlinx.android.synthetic.main.fragment_wish.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.SetName
import ru.spcm.apps.mtgpro.view.adapter.WishListAdapter
import ru.spcm.apps.mtgpro.viewmodel.WishViewModel
import java.util.ArrayList

class WishFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_wish, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, WishViewModel::class.java)
        viewModel.getCards().observe(this, Observer { observeCards(it) })
        viewModel.getWishedSetNames().observe(this, Observer { observeWishedSets(viewModel, it) })

        val adapter = WishListAdapter(null)
        list.layoutManager = GridLayoutManager(context, 3)
        list.adapter = adapter
        adapter.setOnItemClickListener { _, item, _ -> navigator.goToCard(item.id) }

        list.postDelayed({ viewModel.loadWishedCards(viewModel.selectedFilter ?: arrayOf()) }, 200)

        filterToggle.setOnClickListener { filterBlock.toggle() }
    }

    private fun observeCards(data: List<Card>?) {
        if (data != null) {
            val adapter = list.adapter as WishListAdapter
            adapter.setItems(data)
        }
    }

    private fun observeWishedSets(viewModel: WishViewModel, data: List<SetName>?) {
        if (data != null) {
            itemsBlock.removeAllViews()
            val selectedFilters = viewModel.selectedFilter ?: arrayOf()
            data.forEach {
                val checkBox = CheckBox(requireContext())
                checkBox.text = it.setTitle
                checkBox.tag = it.set
                itemsBlock.addView(checkBox)
                checkBox.setOnClickListener {
                    val selected = getSelectedFilters()
                    viewModel.loadWishedCards(selected)
                    setSelectedFiltersTitle(selected)
                }
                if (selectedFilters.contains(it.set)){
                    checkBox.isChecked = true
                }
            }
            setSelectedFiltersTitle(viewModel.selectedFilter ?: arrayOf())
        }
    }

    private fun setSelectedFiltersTitle(selected: Array<String>) {
        if (selected.isEmpty()) {
            filterValues.text = "Все"
        } else {
            val str = StringBuilder()
            selected.forEach {
                if (str.isNotEmpty()) {
                    str.append(", ")
                }
                str.append(it)
            }
            filterValues.text = str.toString()
        }
    }

    private fun getSelectedFilters(): Array<String> {
        val selected = ArrayList<String>()
        for (i in 0 until itemsBlock.childCount) {
            val item: CheckBox = itemsBlock.getChildAt(i) as CheckBox
            if (item.isChecked) {
                selected.add(item.tag as String)
            }
        }
        return selected.toTypedArray()
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Хочу!"
    }

}
