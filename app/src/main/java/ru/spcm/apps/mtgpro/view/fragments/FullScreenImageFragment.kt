package ru.spcm.apps.mtgpro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_full_screen_image.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache

class FullScreenImageFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_full_screen_image, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cardImage.loadImageFromCache(args.getString(ARG_URL) ?: "")
        close.setOnClickListener { _ -> navigator.backTo() }
        cardImage.setOnClickListener { _ -> navigator.backTo() }
    }

    override fun getTitle(): String {
        return ""
    }

    override fun onResume() {
        super.onResume()
        toggleBottomMenu(false)
    }

    override fun onPause() {
        super.onPause()
        toggleBottomMenu(true)
    }

    companion object {

        private const val ARG_ID = "id"
        private const val ARG_URL = "url"

        fun getInstance(id: String, url: String): FullScreenImageFragment {
            val args = Bundle()
            val fragment = FullScreenImageFragment()
            args.putString(ARG_URL, url)
            args.putString(ARG_ID, id)
            fragment.arguments = args
            return fragment
        }
    }
}
