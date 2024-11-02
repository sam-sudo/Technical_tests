package com.ck.pruebatecnica.data.local.entities.entityComplete

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.EpisodeEntity
import com.ck.pruebatecnica.data.local.entities.crossRef.CharacterEpisodeCrossRef

data class CharacterWithEpisodesEntity(
    @Embedded val character: CharacterEntity,
    @Relation(
        parentColumn = "characterId",
        entityColumn = "episodeId",
        associateBy = Junction(CharacterEpisodeCrossRef::class)
    )
    val episodes: List<EpisodeEntity>
)