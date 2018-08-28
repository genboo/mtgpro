package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_price_volatility.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardObserved
import ru.spcm.apps.mtgpro.model.dto.GraphDot
import ru.spcm.apps.mtgpro.tools.format
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

        viewModel.getCards().observe(this, Observer { observeCard(it) })
        viewModel.getData().observe(this, Observer { observeData(it) })

        viewModel.load(args.getString(ARG_ID), getSettings().getCurrentValute())

        watchPrice.setOnClickListener { _ ->
            topEdge.isEnabled = watchPrice.isChecked
            bottomEdge.isEnabled = watchPrice.isChecked
            updateObservable(viewModel)
        }

        topEdge.setOnFocusChangeListener { _, focus -> if (!focus) updateObservable(viewModel) }
        bottomEdge.setOnFocusChangeListener { _, focus -> if (!focus) updateObservable(viewModel) }
    }

    private fun updateObservable(viewModel: PriceVolatilityViewModel) {
        val top: Float = if (topEdge.text.isEmpty()) 0f else topEdge.text.toString().toFloat()
        val bottom: Float = if (bottomEdge.text.isEmpty()) 0f else bottomEdge.text.toString().toFloat()
        val valute = getSettings().getCurrentValute()
        viewModel.updateObserve(args.getString(ARG_ID), watchPrice.isChecked, top / valute, bottom / valute)
    }

    private fun observeData(data: List<GraphDot>?) {
        if (data != null) {
            graph.setData(data)
        }
    }

    private fun observeCard(data: CardObserved?) {
        if (data != null) {
            cardImage.loadImageFromCache(data.imageUrl)
            cardPrice.text = getString(R.string.price_rub, data.price?.format() ?: "")
            cardViol.text = data.diff.format()
            when {
                data.diff == 0f -> cardViol.setTextColor(ContextCompat.getColor(cardViol.context, R.color.colorTextMain))
                data.diff < 0 -> cardViol.setTextColor(ContextCompat.getColor(cardViol.context, R.color.colorNegative))
                else -> cardViol.setTextColor(ContextCompat.getColor(cardViol.context, R.color.colorPositive))
            }
            if (data.observe) {
                watchPrice.isChecked = true
                topEdge.isEnabled = true
                bottomEdge.isEnabled = true
            }
            val top = if (data.top == 0f) data.price?.format() else data.top.format()
            val bottom = if (data.bottom == 0f) data.price?.format() else data.bottom.format()
            topEdge.setText(top)
            bottomEdge.setText(bottom)

            cardInfoGroup.visibility = View.VISIBLE
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
