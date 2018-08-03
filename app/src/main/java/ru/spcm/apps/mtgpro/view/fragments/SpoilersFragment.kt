package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.*
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

/**
 * Список спойлеров
 * Created by gen on 29.06.2018.
 */

class SpoilersFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spoilers, container, false)
        initFragment()
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(state: Bundle?) {
        super.onActivityCreated(state)
        updateToolbar()
        val set = args.getString(ARG_SET)

        val viewModel = getViewModel(this, SpoilersViewModel::class.java)
        viewModel.getSpoilers().observe(this, Observer { observeSpoilers(it) })

        val adapter = SpoilersListAdapter(null)
        val layoutManager = GridLayoutManager(context, viewModel.spanCount)
        list.layoutManager = layoutManager
        list.adapter = adapter
        list.clearOnScrollListeners()
        list.addOnScrollListener(RecyclerViewScrollListener({ viewModel.loadSpoilers(set, it) },
                SpoilersBound.PAGES_SIZE))

        showProgressBar()
        list.postDelayed({
            if (adapter.getSize() == 0) {
                viewModel.loadSpoilers(set, SpoilersBound.PAGES_SIZE)
            }
        }, 200)

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
                    adapter.notifyItemChanged(adapter.itemCount)
                }
            }
        }
    }

    private fun showProgressBar() {
        list.visibility = View.GONE
        progressBlock.visibility = View.VISIBLE
    }

    private fun showContent() {
        progressBlock.fadeOut()
        list.fadeIn()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_toggle_list) {
            val layoutManager = list.layoutManager as GridLayoutManager
            val spanCount = when (layoutManager.spanCount) {
                3 -> 2
                2 -> 1
                else -> 3
            }
            layoutManager.spanCount = spanCount
            val viewModel = getViewModel(this, SpoilersViewModel::class.java)
            viewModel.spanCount = spanCount
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("SPAN_COUNT", (list.layoutManager as GridLayoutManager).spanCount)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.spoilers_menu, menu)
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return args.getString(ARG_NAME)
    }

    companion object {

        private const val SPAN_COUNT = "span_count"
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
