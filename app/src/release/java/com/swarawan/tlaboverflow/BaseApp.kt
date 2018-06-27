package com.swarawan.tlaboverflow

import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

/**
 * Created by rioswarawan on 4/11/18.
 */
abstract class BaseApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

        initApplication()
    }

    abstract fun initApplication()
}