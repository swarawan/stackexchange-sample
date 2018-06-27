package com.kajianmu.deps

import com.swarawan.corelibrary.sharedprefs.CorePreferences
import com.swarawan.tlaboverflow.database.AppPreferences
import com.swarawan.tlaboverflow.network.AppNetworkService
import com.swarawan.tlaboverflow.network.NetworkService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by rioswarawan on 5/2/18.
 */

@Module
class LocalModule {

    @Provides
    @Singleton
    fun providesNetworkService(retrofit: Retrofit): NetworkService =
            retrofit.create(NetworkService::class.java)

    @Provides
    @Singleton
    fun providesAppNetworkService(networkService: NetworkService): AppNetworkService =
            AppNetworkService(networkService)

    @Provides
    @Singleton
    fun providesAppPreference(corePreferences: CorePreferences): AppPreferences =
            AppPreferences(corePreferences)

}