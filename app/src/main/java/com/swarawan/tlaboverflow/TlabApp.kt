package com.swarawan.tlaboverflow

import com.kajianmu.deps.DaggerDefaultComponent
import com.kajianmu.deps.DefaultComponent
import com.swarawan.corelibrary.AppModule
import com.swarawan.corelibrary.CommonDeps
import com.swarawan.corelibrary.CommonDepsProvider

/**
 * Created by Rio Swarawn on 6/27/18.
 */
class TlabApp : BaseApp(), CommonDepsProvider {

    lateinit var defaultComponent: DefaultComponent

    override fun initApplication() {
        defaultComponent = DaggerDefaultComponent.builder()
                .appModule(AppModule(this))
                .build()
        defaultComponent.inject(this)
    }

    override fun getCommonDeps(): CommonDeps = defaultComponent
}