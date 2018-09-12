package ru.spcm.apps.mtgpro.view.fragments

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.view.*
import kotlinx.android.synthetic.main.fragment_settings.*
import ru.spcm.apps.mtgpro.R
import ru.spcm.apps.mtgpro.model.dto.Setting
import ru.spcm.apps.mtgpro.services.AlarmReceiver
import ru.spcm.apps.mtgpro.tools.PermissionsHelper
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
        saveBackup.setOnClickListener { saveBackup(viewModel) }
        restoreBackup.setOnClickListener { restoreBackup(viewModel) }
        updateWatchedPrices.setOnClickListener { updateWatchedPrices() }

        saveBackupAuto.isChecked = getSettings().getBoolean(Setting.Type.AUTO_BACKUP, false)
        saveBackupAuto.setOnClickListener { viewModel.setAutoBackup(saveBackupAuto.isChecked) }

        updateLibraryCardsPrice.isChecked = getSettings().getBoolean(Setting.Type.UPDATE_LIBRARY_CARDS_PRICE, false)
        updateLibraryCardsPrice.setOnClickListener { viewModel.setUpdateLibraryCardsPrice(updateLibraryCardsPrice.isChecked) }

        if (!PermissionsHelper.havePermissionStorage(requireContext())) {
            PermissionsHelper.requestLocationPermissions(this)
            saveBackup.isEnabled = false
            restoreBackup.isEnabled = false
        }
    }

    private fun updateWatchedPrices() {
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        intent.putExtra(AlarmReceiver.FORCE, true)
        activity?.sendBroadcast(intent)
    }

    private fun saveBackup(viewModel: SettingsViewModel) {
        saveBackup.isEnabled = false
        viewModel.backup(requireContext()).observe(this, Observer {
            if (it != null && it) {
                showSnack(R.string.action_saved, null)
                saveBackup.isEnabled = true
            }
        })
    }

    private fun restoreBackup(viewModel: SettingsViewModel) {
        restoreBackup.isEnabled = false
        viewModel.restore(requireContext()).observe(this, Observer {
            if (it != null && it) {
                showSnack(R.string.action_restored, null)
                restoreBackup.isEnabled = true
            }
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PermissionsHelper.PERMISSION_REQUEST_CODE_STORAGE && PermissionsHelper.havePermissionStorage(requireContext())) {
            saveBackup.isEnabled = true
            restoreBackup.isEnabled = true
        }
    }

    override fun getTitle(): String {
        return "Настройки"
    }

}
