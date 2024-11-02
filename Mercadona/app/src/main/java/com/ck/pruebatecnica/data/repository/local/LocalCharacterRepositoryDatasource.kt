package com.ck.pruebatecnica.data.repository.local

import android.util.Log
import com.ck.pruebatecnica.data.local.dao.CharacterDao
import com.ck.pruebatecnica.data.local.dao.EpisodeDao
import com.ck.pruebatecnica.data.local.dao.LocationDao
import com.ck.pruebatecnica.data.local.dao.OriginDao
import com.ck.pruebatecnica.data.local.dao.crossRef.CharacterEpisodeDao
import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.local.entities.entityComplete.CharacterWithEpisodesEntity
import com.ck.pruebatecnica.data.mapper.toDomain
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes
import com.ck.pruebatecnica.domain.repository.local.CharacterRepository
import com.ck.pruebatecnica.domain.repository.local.CharacterWithEpisodeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalCharacterRepositoryDatasource @Inject constructor(
    private val characterDao: CharacterDao,
    private val locationDao: LocationDao,
    private val originDao: OriginDao,
    private val episodeDao: EpisodeDao,
    private val characterEpisodeDao: CharacterEpisodeDao
    ) :
    CharacterRepository, CharacterWithEpisodeRepository {

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

    override suspend fun getAllCharactersWithEpisodes(): List<CharacterWithEpisodes> {
        return withContext(Dispatchers.IO) {
            val characters = characterEpisodeDao.getCharactersWithEpisodes().map { character ->
                val characterLocation = locationDao.getLocationByCharacterId(character.character.characterId!!)
                val characterOrigin = originDao.getOriginByCharacterId(character.character.characterId!!)

                character.toDomain(
                    characterOrigin?.toDomain(),
                    characterLocation?.toDomain()
                )
            }
            (characters ?: emptyList<Character>()) as List<CharacterWithEpisodes>
        }
    }

    override suspend fun getCharacterWithEpisodesByID(characterId: Long): CharacterWithEpisodes? {
        return withContext(Dispatchers.IO) {

            val characterWithEpisodes = characterEpisodeDao.getCharacterWithEpisodes(characterId)

            val characterLocation = locationDao.getLocationByCharacterId(characterId)
            val characterOrigin = originDao.getOriginByCharacterId(characterId)
            Log.w("TAG", "characterLocation $characterLocation")
            Log.w("TAG", "characterLocation $characterOrigin")

            characterWithEpisodes?.toDomain(
                characterOrigin?.toDomain(),
                characterLocation?.toDomain()
            )
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