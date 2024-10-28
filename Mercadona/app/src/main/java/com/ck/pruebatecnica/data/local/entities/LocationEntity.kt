package com.ck.pruebatecnica.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "locations", foreignKeys = [
    ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["characterId"],
        childColumns = ["characterId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
],indices = [Index(value = ["characterId", "name"], unique = true)]
)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val locationId: Long? = null,
    val characterId: Long,
    val name: String,
    val url: String,
)