package com.ck.pruebatecnica.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    val episodeId: Long? = null,
    @ColumnInfo(name = "air_date")
    val airDate: String,
    @ColumnInfo(name = "episode")
    val episodeName: String,
    val characters: List<String>
)
