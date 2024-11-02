package com.ck.pruebatecnica.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.dao.crossRef.CharacterEpisodeDao
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.local.entities.crossRef.CharacterEpisodeCrossRef
import com.ck.pruebatecnica.data.mapper.Converters

@Database(entities = [
    CharacterEntity::class,
    OriginEntity::class,
    LocationEntity::class,
    EpisodeEntity::class,
    CharacterEpisodeCrossRef::class,
    ], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
    abstract fun origenDao(): OriginDao
    abstract fun episodeDao(): EpisodeDao
    abstract fun characterEpisodeDao(): CharacterEpisodeDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
