package com.ck.pruebatecnica.data.repository.local

import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.mapper.toDomain
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.repository.local.CharacterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalCharacterRepositoryDatasource @Inject constructor(
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao,
    private val originDao: OriginDao,
    ) :
    CharacterRepository {

    override suspend fun getAllCharacters(): List<Character> {
        return withContext(Dispatchers.IO) {
            val characters = characterDao.getAllCharacters().map { character ->
                val characterLocation = locationDao.getLocationById(character.characterId!!)
                val characterOrigin = originDao.getOriginById(character.characterId)

                character.toDomain(
                    characterOrigin?.toDomain(),
                    characterLocation?.toDomain()
                )
            }
            (characters ?: emptyList<Character>()) as List<Character>
        }
    }

    override suspend fun insertCharacter(character: CharacterEntity) {
        characterDao.insert(character)
    }

    override suspend fun deleteCharacter(character: CharacterEntity) {
        characterDao.delete(character)
    }

    override suspend fun updateCharacter(character: CharacterEntity) {
        characterDao.update(character)
    }

    override suspend fun insertLocation(location: LocationEntity) {
        locationDao.insertLocation(location)
    }

    override suspend fun updateLocation(location: LocationEntity) {
        locationDao.updateLocation(location)
    }

    override suspend fun insertOrigin(origin: OriginEntity) {
        originDao.insertOrigin(origin)
    }

    override suspend fun updateOrigin(origin: OriginEntity) {
        originDao.updateOrigin(origin)
    }
}