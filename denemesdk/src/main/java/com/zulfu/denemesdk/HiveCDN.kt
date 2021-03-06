package com.zulfu.denemesdk

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters


class HiveCDNWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    override fun doWork(): Result {

        Log.d("HiveCDNBuilder", "WORK IS RUNNING")
        downloadFile("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_1mb.mp4","big_buck_bunny_720p_1mb")
        return Result.success()
    }

    private fun downloadFile(fileURL: String, fileName: String) {

        try {
            val request = DownloadManager.Request(Uri.parse(fileURL))
            request.setDescription("download")
            request.setTitle(fileName)

            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                "$fileName.mp4"
            )
            val manager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)
        } catch (ex: Exception) {
           Log.e("HiveCDNBuilder",ex.toString())
        }

    }
}