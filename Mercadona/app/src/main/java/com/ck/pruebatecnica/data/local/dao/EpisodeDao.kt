package com.ck.pruebatecnica.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.local.entities.entityComplete.EpisodeWithCharactersEntity

@Dao
interface EpisodeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(episode: EpisodeEntity): Long

    @Update
    fun update(episode: EpisodeEntity): Int

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes(): List<EpisodeEntity>

    @Query("SELECT * FROM episodes WHERE  episodeId = :id")
    fun getEpisodesByCharacterId(id: Long): List<EpisodeEntity>?

    @Transaction
    @Query("SELECT * FROM episodes WHERE episodeId = :episodeId")
    fun getEpisodeWithCharacters(episodeId: Int): EpisodeWithCharactersEntity?

    @Query("SELECT * FROM episodes WHERE  episodeId = :id")
    fun getEpisodeById(id: Long): EpisodeEntity?
}