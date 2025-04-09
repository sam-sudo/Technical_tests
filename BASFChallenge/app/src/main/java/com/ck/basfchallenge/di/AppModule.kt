package com.ck.basfchallenge.di

import android.content.Context
import com.ck.basfchallenge.data.local.datasource.BatteryDatasource
import com.ck.basfchallenge.data.local.datasource.NetworkDatasource
import com.ck.basfchallenge.data.repository.BatteryRepositoryImpl
import com.ck.basfchallenge.data.repository.NetworkRepositoryImpl
import com.ck.basfchallenge.domain.repository.BatteryRepository
import com.ck.basfchallenge.domain.repository.NetworkRepository
import com.ck.basfchallenge.domain.usecases.battery.ObserveBatteryInfoUseCase
import com.ck.basfchallenge.domain.usecases.battery.ObserveBatteryLowEventUseCase
import com.ck.basfchallenge.domain.usecases.network.ObserveNetworkStatusUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    //network
    @Provides
    @Singleton
    fun provideLocalNetworkDatasource(@ApplicationContext context: Context): NetworkDatasource{
        return NetworkDatasource(context)
    }

    @Provides
    @Singleton
    fun provideNetworkRepository(networkDatasource: NetworkDatasource): NetworkRepository{
        return NetworkRepositoryImpl(networkDatasource)
    }

    @Provides
    @Singleton
    fun provideObserveNetworkStatusUseCase(networkRepository: NetworkRepository): ObserveNetworkStatusUseCase{
        return ObserveNetworkStatusUseCase(networkRepository)
    }

    //battery
    @Provides
    @Singleton
    fun provideLocalBatteryDataSource(@ApplicationContext context: Context): BatteryDatasource {
        return BatteryDatasource(context)
    }

    @Provides
    @Singleton
    fun provideBatteryRepository(batteryDatasource: BatteryDatasource): BatteryRepository{
        return BatteryRepositoryImpl(batteryDatasource)
    }

    @Provides
    @Singleton
    fun provideObserveBatteryStatusUseCase(batteryRepository: BatteryRepository): ObserveBatteryInfoUseCase{
        return ObserveBatteryInfoUseCase(batteryRepository)
    }

    @Provides
    @Singleton
    fun provideObserveBatteryLowEventUseCase(batteryRepository: BatteryRepository): ObserveBatteryLowEventUseCase{
        return ObserveBatteryLowEventUseCase(batteryRepository)
    }
}