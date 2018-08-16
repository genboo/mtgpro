package ru.spcm.apps.mtgpro.view.fragments

import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.tools.PermissionsHelper
import ru.spcm.apps.mtgpro.view.activities.MainActivity
import ru.spcm.apps.mtgpro.view.components.slideIn
import ru.spcm.apps.mtgpro.view.components.slideOut
import ru.spcm.apps.mtgpro.viewmodel.SettingsViewModel

class SettingsFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        initFragment()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateToolbar()

        val viewModel = getViewModel(this, SettingsViewModel::class.java)
        saveBackup.setOnClickListener { _ -> saveBackup(viewModel) }
        restoreBackup.setOnClickListener { _ -> restoreBackup(viewModel) }
        if (!PermissionsHelper.havePermissionStorage(requireContext())) {
            PermissionsHelper.requestLocationPermissions(this)
            saveBackup.isEnabled = false
            restoreBackup.isEnabled = false
        }
    }

    private fun saveBackup(viewModel: SettingsViewModel) {
        if (viewModel.backup(requireContext())) {
            showSnack(R.string.action_saved, null)
        }
    }

    private fun restoreBackup(viewModel: SettingsViewModel) {
        if (viewModel.restore(requireContext())) {
            showSnack(R.string.action_restored, null)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsHelper.PERMISSION_REQUEST_CODE_STORAGE && PermissionsHelper.havePermissionStorage(requireContext())) {
            saveBackup.isEnabled = true
            restoreBackup.isEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        toggleBottomMenu(false)
    }

    override fun onPause() {
        super.onPause()
        toggleBottomMenu(true)
    }

    override fun inject() {
        component?.inject(this)
    }

    override fun getTitle(): String {
        return "Настройки"
    }

}
