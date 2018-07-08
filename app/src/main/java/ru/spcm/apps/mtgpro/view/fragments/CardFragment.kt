package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Spinner
import kotlinx.android.synthetic.main.fragment_card.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.*
import ru.spcm.apps.mtgpro.tools.OracleReplacer
import ru.spcm.apps.mtgpro.view.adapter.LibrarySelectAdapter
import ru.spcm.apps.mtgpro.view.adapter.ReprintListAdapter
import ru.spcm.apps.mtgpro.view.components.ExpandListener
import ru.spcm.apps.mtgpro.view.components.NumberCounterView
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache
import ru.spcm.apps.mtgpro.viewmodel.CardViewModel

/**
 * Карта
 * Created by gen on 29.06.2018.
 */

class CardFragment : BaseFragment() {

    lateinit var card: Card

    private lateinit var addDialog: AlertDialog
    private lateinit var librarySelector: Spinner

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_card, container, false)
        initFragment()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, CardViewModel::class.java)
        viewModel.getCards().observe(this, Observer { observeCards(it) })
        viewModel.getWish().observe(this, Observer { observeWish(it) })
        viewModel.getLibraries().observe(this, Observer { observeLibraries(it) })
        viewModel.loadCard(args.getString(ARG_ID))
        mainBlock.postDelayed({ viewModel.loadLibraries() }, 200)

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

        addToLibrary.setOnClickListener { addDialog.show() }

        initAddToLibraryDialog(viewModel)
    }


    private fun observeCards(data: List<CardLocal>?) {
        if (data != null) {
            if (data.isNotEmpty()) {
                val firstCard = data[0]
                updateTitle(firstCard.card.name)
                cardImage.loadImageFromCache(firstCard.card.imageUrl)
                cardImage.setOnClickListener { navigator.goToImage(firstCard.card.id, firstCard.card.imageUrl) }
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

    private fun observeLibraries(data: List<LibraryInfo>?) {
        if (data != null) {
            val adapter = LibrarySelectAdapter(requireContext(), data)
            librarySelector.adapter = adapter
        }
    }

    private fun initAddToLibraryDialog(model: CardViewModel) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_card, mainBlock, false)
        librarySelector = dialogView.findViewById(R.id.spn_card_library)
        val countText = dialogView.findViewById<NumberCounterView>(R.id.counterBlock)
        addDialog = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Добавить карту")
                .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Ok") { _, _ ->
                    val selectedLibrary = librarySelector.selectedItem as LibraryInfo
                    if (selectedLibrary.id != 0L) {
                        val item = LibraryCard(selectedLibrary.id, card.id)
                        item.count = countText.getCount()
                        model.addCard(item)
                    }
                    showSnack(R.string.action_added, null)
                }
                .create()
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

