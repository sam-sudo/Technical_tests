package com.ck.pruebatecnica.data.local.entities.crossRef

import androidx.room.Entity

@Entity(primaryKeys = ["characterId", "episodeId"])
data class CharacterEpisodeCrossRef(
    val characterId: Long,
    val episodeId: Long
)
