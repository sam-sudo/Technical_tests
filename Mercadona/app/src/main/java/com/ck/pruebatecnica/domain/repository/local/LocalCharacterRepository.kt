package com.ck.pruebatecnica.domain.repository.local

import com.ck.pruebatecnica.data.local.entities.CharacterEntity
import com.ck.pruebatecnica.data.local.entities.LocationEntity
import com.ck.pruebatecnica.data.local.entities.OriginEntity
import com.ck.pruebatecnica.data.local.entities.entityComplete.CharacterWithEpisodesEntity
import com.ck.pruebatecnica.data.repository.local.LocalCharacterRepositoryDatasource
import com.ck.pruebatecnica.domain.model.Character
import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes
import javax.inject.Inject

class LocalCharacterRepository  @Inject constructor(
    private val characterDatasource: LocalCharacterRepositoryDatasource,
) :
    CharacterRepository,CharacterWithEpisodeRepository {

    override suspend fun getAllCharacters(): List<Character> {
        return characterDatasource.getAllCharacters()
    }

    override suspend fun getCharacterByID(id: Long): Character? {
        return characterDatasource.getCharacterByID(id)
    }

    override suspend fun insertCharacter(character: CharacterEntity) {
        characterDatasource.insertCharacter(character)
    }

    override suspend fun deleteCharacter(character: CharacterEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCharacter(character: CharacterEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertLocation(location: LocationEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateLocation(location: LocationEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrigin(origin: OriginEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateOrigin(origin: OriginEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun getCharacterWithEpisodesByID(id: Long): CharacterWithEpisodes? {
        return characterDatasource.getCharacterWithEpisodesByID(id)

    }

    override suspend fun getAllCharactersWithEpisodes(): List<CharacterWithEpisodes> {
        return characterDatasource.getAllCharactersWithEpisodes()

    }

}