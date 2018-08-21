package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_price_volatility.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.dto.GraphDot
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import ru.spcm.apps.mtgpro.viewmodel.PriceVolatilityViewModel

class PriceVolatilityFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_price_volatility, container, false)
        setHasOptionsMenu(true)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, PriceVolatilityViewModel::class.java)

        viewModel.getCards().observe(this, Observer { observeCards(it) })
        viewModel.getData().observe(this, Observer { observeData(it) })

        viewModel.load(args.getString(ARG_ID))
    }

    private fun observeData(data: List<GraphDot>?) {
        if (data != null) {
            graph.setData(data)
        }
    }

    private fun observeCards(data: List<CardLocal>?) {
        if (data != null) {
            val card = data[0].card
            cardName.text = card.name
            cardImage.loadImageFromCache(card.imageUrl)
            if (data.size > 1) {
                val secondCard = data[1].card
                cardImageSecond.loadImageFromCache(secondCard.imageUrl)
            }
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Изменение цены"
    }

    companion object {

        private const val ARG_ID = "id"

        fun getInstance(id: String): PriceVolatilityFragment {
            val args = Bundle()
            args.putString(ARG_ID, id)
            val fragment = PriceVolatilityFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
