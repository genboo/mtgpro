package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_libraries.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Library
import ru.spcm.apps.mtgpro.model.dto.LibraryInfo
import ru.spcm.apps.mtgpro.view.adapter.LibrariesListAdapter
import ru.spcm.apps.mtgpro.viewmodel.LibrariesViewModel

class LibrariesFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_libraries, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, LibrariesViewModel::class.java)
        viewModel.libraries.observe(this, Observer { observeLibraries(it) })

        val adapter = LibrariesListAdapter(null)
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
        adapter.setOnItemClickListener { _, libraryInfo, _ -> navigator.goToLibrary(libraryInfo.id) }

        fab.setOnClickListener {
            val view = layoutInflater.inflate(R.layout.dialog_add_library, main, false)
            val addDialog = AlertDialog.Builder(requireContext())
                    .setView(view)
                    .setTitle("Добавить колоду")
                    .setPositiveButton("Ok") { _, _ ->
                        val libraryName = view.findViewById<EditText>(R.id.et_library_name)
                        val name = libraryName.text.toString()
                        if (name.isNotEmpty()) {
                            viewModel.save(Library(name))
                            showSnack(R.string.action_add_to_library, null)
                        }
                    }
            addDialog.show()
        }
    }

    private fun observeLibraries(data: List<LibraryInfo>?) {
        if (data != null) {
            (list.adapter as LibrariesListAdapter).setItems(data)
        }
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Колоды"
    }

}
