package ru.spcm.apps.mtgpro.view.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.view.components.loadImageFromCache


class ImageFragment : Fragment() {

    private val args: Bundle by lazy { arguments ?: Bundle() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_image, container, false)
        val image = view.findViewById<ImageView>(R.id.cardImage)
        image.loadImageFromCache(args.getString(ARG_URL) ?: "")
        return view
    }

    companion object {

        private const val ARG_URL = "url"
        private const val ARG_ID = "id"

        fun getInstance(url: String, id: String): ImageFragment {
            val args = Bundle()
            args.putString(ARG_URL, url)
            args.putString(ARG_ID, id)
            val fragment = ImageFragment()
            fragment.arguments = args
            return fragment
        }
    }

}