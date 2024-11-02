package com.ck.pruebatecnica.domain.repository.local

import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.domain.model.Character

interface CharacterRepository {
    suspend fun getAllCharacters(): List<Character>
    suspend fun getCharacterByID(id: Long): Character?
    suspend fun insertCharacter(character: CharacterEntity)
    suspend fun deleteCharacter(character: CharacterEntity)
    suspend fun updateCharacter(character: CharacterEntity)
    suspend fun insertLocation(location: LocationEntity)
    suspend fun updateLocation(location: LocationEntity)
    suspend fun insertOrigin(origin: OriginEntity)
    suspend fun updateOrigin(origin: OriginEntity)
}