package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_price_volatility.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.CardObserved
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

        viewModel.getCards().observe(this, Observer { observeCard(it) })
        viewModel.getData().observe(this, Observer { observeData(it) })

        viewModel.load(args.getString(ARG_ID))

        watchPrice.setOnClickListener { _ ->
            topEdge.isEnabled = watchPrice.isChecked
            bottomEdge.isEnabled = watchPrice.isChecked
            viewModel.updateObserve(args.getString(ARG_ID), watchPrice.isChecked, topEdge.text.toString().toFloat(), bottomEdge.text.toString().toFloat())
        }

        topEdge.setOnFocusChangeListener{ _, focus ->
            if(!focus){
                viewModel.updateObserve(args.getString(ARG_ID), watchPrice.isChecked, topEdge.text.toString().toFloat(), bottomEdge.text.toString().toFloat())
            }
        }

        bottomEdge.setOnFocusChangeListener{ _, focus ->
            if(!focus){
                viewModel.updateObserve(args.getString(ARG_ID), watchPrice.isChecked, topEdge.text.toString().toFloat(), bottomEdge.text.toString().toFloat())
            }
        }
    }

    private fun observeData(data: List<GraphDot>?) {
        if (data != null) {
            graph.setData(data)
        }
    }

    private fun observeCard(data: CardObserved?) {
        if (data != null) {
            cardImage.loadImageFromCache(data.imageUrl)
            cardPrice.text = data.price
            cardViol.text = data.diff
            if (data.observe) {
                watchPrice.isChecked = true
                topEdge.isEnabled = true
                bottomEdge.isEnabled = true
            }
            val top = if (data.top == 0f) data.price else data.top.toString()
            val bottom = if (data.bottom == 0f) data.price else data.bottom.toString()
            topEdge.setText(top)
            bottomEdge.setText(bottom)
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
