package com.swarawan.tlaboverflow

import android.os.StrictMode
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary

/**
 * Created by rioswarawan on 4/11/18.
 */
abstract class BaseApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        initApplication()
        initLeakCanary()
        enableStrictMode()
    }

    abstract fun initApplication()

    private fun enableStrictMode() {
        val threadPolicy = StrictMode.ThreadPolicy.Builder().apply {
            detectDiskReads()
            detectDiskWrites()
            detectNetwork()
            penaltyLog()
        }
        StrictMode.setThreadPolicy(threadPolicy.build())

        val vmPolicy = StrictMode.VmPolicy.Builder().apply {
            detectLeakedSqlLiteObjects()
            detectLeakedClosableObjects()
            penaltyLog()
        }
        StrictMode.setVmPolicy(vmPolicy.build())
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) return
        LeakCanary.install(this)
    }
}