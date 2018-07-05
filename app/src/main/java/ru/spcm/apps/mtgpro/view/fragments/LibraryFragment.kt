package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Card
import ru.spcm.apps.mtgpro.viewmodel.CardViewModel
import javax.inject.Inject

/**
 * Карта
 * Created by gen on 29.06.2018.
 */

class LibraryFragment : BaseFragment() {

    lateinit var card: Card

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_library, container, false)
        initFragment()
        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(CardViewModel::class.java)

    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return ""
    }

    companion object {

        private const val ARG_ID = "id"

        fun getInstance(id: Long): LibraryFragment {
            val args = Bundle()
            args.putLong(ARG_ID, id)
            val fragment = LibraryFragment()
            fragment.arguments = args
            return fragment
        }
    }

}

