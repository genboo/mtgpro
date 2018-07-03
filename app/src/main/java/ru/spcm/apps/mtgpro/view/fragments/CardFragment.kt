package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import kotlinx.android.synthetic.main.fragment_card.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.model.dto.CardLocal
import ru.spcm.apps.mtgpro.model.dto.WishedCard
import ru.spcm.apps.mtgpro.tools.OracleReplacer
import ru.spcm.apps.mtgpro.view.adapter.ReprintListAdapter
import ru.spcm.apps.mtgpro.view.components.ExpandListener
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import ru.spcm.apps.mtgpro.viewmodel.CardViewModel
import javax.inject.Inject

/**
 * Карта
 * Created by gen on 29.06.2018.
 */

class CardFragment : BaseFragment() {

    lateinit var card: Card

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
        viewModel.getCards().observe(this, Observer { observeCards(it) })
        viewModel.getWish().observe(this, Observer { observeWish(it) })
        viewModel.loadCard(args.getString(ARG_ID))

        val adapter = ReprintListAdapter(null)
        val manager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        reprints.adapter = adapter
        reprints.layoutManager = manager

        val task = { viewModel.updateCard(card) }
        val handler = Handler()
        counterBlock.setOnChangeListener {
            card.count = it
            handler.removeCallbacksAndMessages(null)
            handler.postDelayed(task, 1000)
        }

        addToWish.setOnClickListener {
            viewModel.updateWish(card.id, it.tag as Boolean)
        }
    }

    private fun observeCards(data: List<CardLocal>?) {
        if (data != null) {
            if (data.isNotEmpty()) {
                val firstCard = data[0]
                updateTitle(firstCard.card.name)
                cardImage.loadImageFromCache(firstCard.card.imageUrl)
                cardName.text = firstCard.card.name
                cardRarity.setColorFilter(ContextCompat.getColor(requireContext(), firstCard.card.getSetIconColor()), PorterDuff.Mode.SRC_IN)
                cardRarity.setImageDrawable(resources.getDrawable(firstCard.card.getSetIcon(), requireContext().theme))
                cardNumber.text = String.format("%s %s", firstCard.card.set, firstCard.card.numberFormatted)
                cardManaCost.text =
                        OracleReplacer.getText(firstCard.card.manaCost ?: "", requireActivity())

                cardText.setOnClickListener { cardOracle.toggle() }
                cardOracle.text = OracleReplacer.getText(firstCard.card.text
                        ?: "", requireActivity())
                cardOracle.setExpandListener(ExpandListener(cardOracleArrow))

                cardRulesTitle.setOnClickListener { cardRules.toggle() }
                cardRules.text = OracleReplacer.getText(firstCard.card.rulesText
                        ?: "", requireActivity())
                cardRules.setExpandListener(ExpandListener(cardRulesArrow))

                counterBlock.setCount(firstCard.card.count)

                (reprints.adapter as ReprintListAdapter).setItems(firstCard.reprints)

                card = firstCard.card

            }
        }
    }

    private fun observeWish(data: WishedCard?) {
        if (data == null) {
            addToWish.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart_outline))
            addToWish.tag = true
        } else {
            addToWish.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_heart))
            addToWish.tag = false
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

