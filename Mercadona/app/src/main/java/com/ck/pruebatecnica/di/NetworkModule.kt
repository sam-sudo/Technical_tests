package com.ck.pruebatecnica.di

import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.repository.remote.rickMortyApi.RemoteCharacterDatasource
import com.ck.pruebatecnica.data.repository.remote.rickMortyApi.CharacterService
import com.ck.pruebatecnica.domain.repository.remote.CharacterRepository
import com.ck.pruebatecnica.domain.repository.remote.RemoteCharacterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideCharacterRepository(
        remoteCharacterDatasource: RemoteCharacterDatasource
    ): CharacterRepository {
        return RemoteCharacterRepository(remoteCharacterDatasource)
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    @Singleton
    @Provides
    fun provideCharacterDatasource(
        characterService: CharacterService,
        characterDao: CharacterDao,
        locationDao: LocationDao,
        origenDao: OriginDao,
        episodeDao: EpisodeDao
    ): RemoteCharacterDatasource {
        return RemoteCharacterDatasource(
            characterService,
            characterDao,
            locationDao,
            origenDao,
            episodeDao
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_URL_PLACES)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

private const val API_URL_PLACES = "https://rickandmortyapi.com/"
