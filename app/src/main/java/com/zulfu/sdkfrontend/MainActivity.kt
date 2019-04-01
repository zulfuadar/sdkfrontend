package com.zulfu.sdkfrontend

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.zulfu.denemesdk.HiveCDNBuilder


class MainActivity : AppCompatActivity() {
    val TAG = "HiveCDNBuilder"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        isWriteStoragePermissionGranted()
        isReadStoragePermissionGranted()

    }

    private fun isReadStoragePermissionGranted(): Boolean {
        if (Build.VERSION.SDK_INT >= 23) {
           val isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
            return if(isGranted == PackageManager.PERMISSION_GRANTED){

                Log.v(TAG, "Permission is granted1")
                true
            } else {

                Log.v(TAG, "Permission is revoked1")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 3)
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1")
            return true
        }
    }

    private fun isWriteStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            val isGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            if (isGranted == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted2")
                HiveCDNBuilder.build(this)
                true
            } else {

                Log.v(TAG, "Permission is revoked2")
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 2)
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2")
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults.isNotEmpty()) {
            when (requestCode) {
                2 -> {
                    Log.d(TAG, "External storage2")
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                        //resume tasks needing this permission
                        HiveCDNBuilder.build(this)
                    } else {
                    }
                }

                3 -> {
                    Log.d(TAG, "External storage1")
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                        //resume tasks needing this permission
                    } else {

                    }
                }
            }
        }
    }
}

