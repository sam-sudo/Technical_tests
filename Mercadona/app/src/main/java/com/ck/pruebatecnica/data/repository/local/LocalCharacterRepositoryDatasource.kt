package com.ck.pruebatecnica.data.repository.local

import android.util.Log
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.mapper.toDomain
import com.ck.pruebatecnica.data.model.Episode
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.repository.local.CharacterRepository
import com.ck.pruebatecnica.domain.repository.local.EpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalCharacterRepositoryDatasource @Inject constructor(
    private val characterDao: CharacterDao,
    private val episodeDao: EpisodeDao,
    private val locationDao: LocationDao,
    private val originDao: OriginDao,
    ) :
    CharacterRepository,EpisodeRepository {

    override suspend fun getAllCharacters(): List<Character> {
        return withContext(Dispatchers.IO) {
            val characters = characterDao.getAllCharacters().map { character ->
                val characterLocation = locationDao.getLocationByCharacterId(character.characterId!!)
                val characterOrigin = originDao.getOriginByCharacterId(character.characterId)

                character.toDomain(
                    characterOrigin?.toDomain(),
                    characterLocation?.toDomain()
                )
            }
            (characters ?: emptyList<Character>()) as List<Character>
        }
    }

    override suspend fun getCharacterByID(id: Long): Character? {
        return withContext(Dispatchers.IO) {
            val characterId = id
            Log.w("TAG","characterId $characterId")

            val character = characterDao.getCharacterById(characterId)

            val characterLocation = locationDao.getLocationByCharacterId(characterId)
            val characterOrigin = originDao.getOriginByCharacterId(characterId)

            Log.w("TAG","characterLocation $characterLocation")
            Log.w("TAG","characterLocation $characterOrigin")

            character?.toDomain(
                characterOrigin?.toDomain(),
                characterLocation?.toDomain()
            )
        }
    }

    override suspend fun getEpisodeById(id: Long): Episode? {
        return withContext(Dispatchers.IO) {
            val characterId = id
            Log.w("TAG","Episode Id $characterId")

            val episode = episodeDao.getEpisodeById(characterId)

            Log.w("TAG","episode $episode")

            episode?.toDomain()
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