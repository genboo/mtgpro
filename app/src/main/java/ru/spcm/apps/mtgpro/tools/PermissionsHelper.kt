package ru.spcm.apps.mtgpro.tools

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import ru.spcm.apps.mtgpro.view.fragments.BaseFragment

/**
 * Управление разрешениями приложения
 * Created by gen on 11.01.2018.
 */

object PermissionsHelper {
    const val PERMISSION_REQUEST_CODE_STORAGE = 1

    fun havePermissionStorage(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermissions(fragment: BaseFragment) {
        fragment.requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE_STORAGE)
    }
}
