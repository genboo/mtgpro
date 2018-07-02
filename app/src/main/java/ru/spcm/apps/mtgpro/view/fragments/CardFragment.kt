package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_card.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.tools.OracleReplacer
import ru.spcm.apps.mtgpro.view.adapter.ReprintListAdapter
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import ru.spcm.apps.mtgpro.viewmodel.CardViewModel
import javax.inject.Inject

/**
 * Карта
 * Created by gen on 29.06.2018.
 */

class CardFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_card, container, false)
        initFragment()
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CardViewModel::class.java)
        viewModel.getCard().observe(this, Observer { observeCard(it) })
        viewModel.loadCard(args.getString(ARG_ID))

        val adapter = ReprintListAdapter(null)
        val manager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        reprints.adapter = adapter
        reprints.layoutManager = manager
    }

    private fun observeCard(data: List<CardLocal>?) {
        if (data != null) {
            if (data.isNotEmpty()) {
                val firstCard = data[0]
                cardImage.loadImageFromCache(firstCard.card.imageUrl)
                cardName.text = firstCard.card.name
                cardRarity.setColorFilter(ContextCompat.getColor(requireContext(), firstCard.card.getSetIconColor()), PorterDuff.Mode.SRC_IN)
                cardRarity.setImageDrawable(resources.getDrawable(firstCard.card.getSetIcon(), requireContext().theme))
                cardNumber.text = String.format("%s %s", firstCard.card.set, firstCard.card.numberFormatted)
                cardManaCost.text =
                        OracleReplacer.getText(firstCard.card.manaCost ?: "", requireActivity())

                updateTitle(firstCard.card.name)

                (reprints.adapter as ReprintListAdapter).setItems(firstCard.reprints)
            }
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

        fun getInstance(id: String): CardFragment {
            val args = Bundle()
            args.putString(ARG_ID, id)
            val fragment = CardFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

