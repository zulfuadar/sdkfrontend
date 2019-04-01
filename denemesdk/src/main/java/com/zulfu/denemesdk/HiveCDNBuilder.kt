package com.zulfu.denemesdk

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import android.Manifest.permission
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import androidx.work.*


class HiveCDNBuilder{
    companion object {
        @SuppressLint("RestrictedApi")
        fun build(activity: LifecycleOwner){

            val constraints = Constraints()
            constraints.setRequiresCharging(true)
            constraints.requiresCharging()
            constraints.requiredNetworkType = NetworkType.CONNECTED

            val workRequest = OneTimeWorkRequestBuilder<HiveCDNWorker>().setConstraints(constraints).build()
            WorkManager.getInstance().enqueue(workRequest)

            WorkManager.getInstance().getWorkInfoByIdLiveData(workRequest.id).observe(activity, Observer {
                if(it.state == WorkInfo.State.SUCCEEDED){
                Log.d("HiveCDNBuilder","WORK IS DONE")
                }
            })

        }
    }




}