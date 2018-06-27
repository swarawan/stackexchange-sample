package com.kajianmu.deps

import com.swarawan.corelibrary.AppModule
import com.swarawan.corelibrary.CommonDeps
import com.swarawan.corelibrary.eventbus.EventBusModule
import com.swarawan.corelibrary.firebase.FirebaseModule
import com.swarawan.corelibrary.network.NetworkModule
import com.swarawan.corelibrary.sharedprefs.CorePreferencesModule
import com.swarawan.tlaboverflow.TlabApp
import com.swarawan.tlaboverflow.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by rioswarawan on 4/12/18.
 */

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        EventBusModule::class,
        FirebaseModule::class,
        CorePreferencesModule::class,
        NetworkModule::class,
        LocalModule::class
))
interface DefaultComponent : CommonDeps {

    fun inject(app: TlabApp)
    fun inject(activity: MainActivity)
}