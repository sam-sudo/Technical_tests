package com.ck.pruebatecnica.domain.repository.local

import com.ck.pruebatecnica.domain.model.CharacterWithEpisodes

interface CharacterWithEpisodeRepository {
    suspend fun getCharacterWithEpisodesByID(id: Long): CharacterWithEpisodes?
    suspend fun getAllCharactersWithEpisodes(): List<CharacterWithEpisodes>

}