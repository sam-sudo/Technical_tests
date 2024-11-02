package com.ck.pruebatecnica.data.local.dao.crossRef

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.ck.pruebatecnica.data.local.entities.crossRef.CharacterEpisodeCrossRef
import com.ck.pruebatecnica.data.local.entities.entityComplete.CharacterWithEpisodesEntity

@Dao
interface CharacterEpisodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCrossRefs(crossRefs: List<CharacterEpisodeCrossRef>)

    @Transaction
    @Query("SELECT * FROM characters")
    fun getCharactersWithEpisodes(): List<CharacterWithEpisodesEntity>

    @Transaction
    @Query("SELECT * FROM characters WHERE characterId = :characterId")
    fun getCharacterWithEpisodes(characterId: Long): CharacterWithEpisodesEntity?
}