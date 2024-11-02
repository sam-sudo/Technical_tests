package com.ck.pruebatecnica.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val episodeId: Long? = null,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("name")
    val name: String, // Agregar esta propiedad para capturar el nombre del episodio
)

