package com.crm.siska.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permission {
    fun isStorageOk(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestStoragePermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PICK_IMAGE
        )
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }
}
