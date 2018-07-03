package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_spoilers.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.model.tools.Status
import ru.spcm.apps.mtgpro.repository.bounds.SpoilersBound
import ru.spcm.apps.mtgpro.view.adapter.RecyclerViewScrollListener
import ru.spcm.apps.mtgpro.view.adapter.SpoilersListAdapter
import ru.spcm.apps.mtgpro.view.components.fadeIn
import ru.spcm.apps.mtgpro.view.components.fadeOut
import ru.spcm.apps.mtgpro.viewmodel.SpoilersViewModel
import javax.inject.Inject

/**
 * Список спойлеров
 * Created by gen on 29.06.2018.
 */

class SpoilersFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spoilers, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()
        val set = args.getString(ARG_SET)

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(SpoilersViewModel::class.java)
        viewModel.getSpoilers().observe(this, Observer { observeSpoilers(it) })

        val adapter = SpoilersListAdapter(null)
        val layoutManager = GridLayoutManager(context, 3)
        list.layoutManager = layoutManager
        list.adapter = adapter
        list.clearOnScrollListeners()
        list.addOnScrollListener(RecyclerViewScrollListener({ viewModel.loadSpoilers(set, it) },
                SpoilersBound.PAGES_SIZE))

        showProgressBar()
        list.postDelayed({ viewModel.loadSpoilers(set, 1) }, 200)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (adapter.getItemViewType(position) == SpoilersListAdapter.TYPE_LOADING) layoutManager.spanCount else 1
            }
        }

        adapter.setOnItemClickListener { _, item, _ -> navigator.goToCard(item.id) }
    }

    private fun observeSpoilers(data: Resource<List<Card>>?) {
        if (data != null) {
            val adapter = list.adapter as SpoilersListAdapter
            adapter.setLoading(data.status == Status.LOADING)
            if (data.status == Status.SUCCESS && data.data != null) {
                if (adapter.getItems().isEmpty()) {
                    showContent()
                }
                val sameCount = data.data.size == adapter.getSize()
                adapter.setItems(data.data)
                if (sameCount) {
                    adapter.notifyItemChanged(adapter.getSize())
                }
            }
        }
    }

    private fun showProgressBar() {
        list.visibility = View.GONE
        progressBlock.visibility = View.VISIBLE
    }

    private fun showContent() {
        progressBlock.fadeOut(progressBlock.parent as ViewGroup)
        list.fadeIn(list.parent as ViewGroup)
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return args.getString(ARG_NAME)
    }

    companion object {

        private const val ARG_SET = "set"
        private const val ARG_NAME = "name"

        fun getInstance(set: String, name: String): SpoilersFragment {
            val args = Bundle()
            args.putString(ARG_SET, set)
            args.putString(ARG_NAME, name)
            val fragment = SpoilersFragment()
            fragment.arguments = args
            return fragment
        }
    }

}
