package com.ck.pruebatecnica.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "origins", foreignKeys = [
    ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["characterId"],
        childColumns = ["characterId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )
],indices = [Index(value = ["characterId", "name"], unique = true)])
data class OriginEntity(
    @PrimaryKey(autoGenerate = true) val originID: Long? = null,
    val characterId: Long,
    val name: String,
    val url: String,
)


