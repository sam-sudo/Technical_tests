package com.ck.pruebatecnica.di

import android.app.Application
import com.ck.pruebatecnica.data.local.AppDatabase
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.dao.crossRef.CharacterEpisodeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return AppDatabase.getDatabase(app)
    }

    @Provides
    @Singleton
    fun provideCharacterDao(database: AppDatabase): CharacterDao {
        return database.characterDao()
    }

    @Provides
    @Singleton
    fun provideOriginDao(database: AppDatabase): LocationDao {
        return database.locationDao()
    }

    @Provides
    @Singleton
    fun provideLocationDao(database: AppDatabase): OriginDao {
        return database.origenDao()
    }

    @Provides
    @Singleton
    fun provideEpisodeDao(database: AppDatabase): EpisodeDao {
        return database.episodeDao()
    }

    @Provides
    @Singleton
    fun provideCharacterEpisodeDao(database: AppDatabase): CharacterEpisodeDao {
        return database.characterEpisodeDao()
    }
}