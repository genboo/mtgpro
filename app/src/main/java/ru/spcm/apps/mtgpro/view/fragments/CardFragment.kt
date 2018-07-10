package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var addChildDialog: AlertDialog
    private lateinit var librarySelector: Spinner

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

        val viewModel = getViewModel(this, CardViewModel::class.java)
        viewModel.getCards().observe(this, Observer { observeCards(it) })
        viewModel.getWish().observe(this, Observer { observeWish(it) })
        viewModel.getLibraries().observe(this, Observer { observeLibraries(it) })
        viewModel.getLibrariesByCard().observe(this, Observer { observeLibrariesByCard(viewModel, it) })
        viewModel.loadCard(args.getString(ARG_ID))
        mainBlock.postDelayed({ viewModel.loadLibraries() }, 200)

        val adapter = ReprintListAdapter(null)
        val manager = LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false)
        reprints.adapter = adapter
        reprints.layoutManager = manager

        val task = Runnable { viewModel.updateCard(card) }
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

        cardName.setOnClickListener { _ ->
            val clipboard = requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("multivers id", cardName.tag.toString())
            clipboard.primaryClip = clip
            showSnack(R.string.action_copy_to_clip, null)
        }

        initAddToLibraryDialog(viewModel)
        initAddChildDialog(viewModel)
    }


    private fun observeCards(data: List<CardLocal>?) {
        if (data != null && data.isNotEmpty()) {
            val firstCard = data[0]
            updateTitle(firstCard.card.name)
            cardImage.loadImageFromCache(firstCard.card.imageUrl)
            cardImage.setOnClickListener { navigator.goToImage(firstCard.card.id, firstCard.card.imageUrl) }
            cardName.text = firstCard.card.name
            cardName.tag = firstCard.card.id
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

            if (data.size > 1) {
                val secondCard = data[1]
                cardImageSecond.loadImageFromCache(secondCard.card.imageUrl)
                cardImageSecond.setOnClickListener { navigator.goToImage(secondCard.card.id, secondCard.card.imageUrl) }
                cardOracle.text = OracleReplacer.getText(firstCard.card.text + "\n\n"
                        + secondCard.card.name + "\n\n"
                        + secondCard.card.text, requireActivity())
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

    private fun observeLibrariesByCard(viewModel: CardViewModel, data: List<LibraryInfo>?) {
        if (data != null) {
            librariesBlock.removeAllViews()
            val handler = Handler()
            data.forEach {
                val view = layoutInflater.inflate(R.layout.layout_cards_counter, librariesBlock, false)
                val number = view.findViewById<NumberCounterView>(R.id.counterBlock)
                val title = view.findViewById<TextView>(R.id.libraryName)
                number.setCount(it.cardsCount)
                title.text = it.name
                val item = LibraryCard(it.id, "")
                val updateCardTask = Runnable { viewModel.updateLibraryCard(item) }
                number.setOnChangeListener {
                    item.cardId = card.id
                    item.count = it
                    handler.removeCallbacks(updateCardTask)
                    handler.postDelayed(updateCardTask, 1000)
                }
                librariesBlock.addView(view)
            }
        }
    }

    private fun initAddChildDialog(viewModel: CardViewModel) {
        addChildDialog = AlertDialog.Builder(requireContext())
                .setView(layoutInflater.inflate(R.layout.dialog_add_child, mainBlock, false))
                .setTitle("Указать вторую сторону")
                .setNegativeButton("Отмена") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Ok") { _, _ ->
                    val child = addChildDialog.findViewById<EditText>(R.id.et_card_id)
                    if (child != null && child.text.isNotEmpty()) {
                        viewModel.updateLink(child.text.toString(), card.id)
                        showSnack(R.string.action_added, null)
                    }
                }
                .create()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.nav_link) {
            addChildDialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.card_menu, menu)
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

